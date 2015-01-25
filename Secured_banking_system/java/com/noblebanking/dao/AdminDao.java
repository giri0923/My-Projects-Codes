package com.noblebanking.dao;


import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.noblebanking.action.UserController;
import com.noblebanking.bean.IDBean;
import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.UserBean;
import com.noblebanking.util.GmailSmtpSSL;
import com.noblebanking.util.IDRowMapper;
import com.noblebanking.util.InternalUserRowMapper;
import com.noblebanking.util.OTP;
import com.noblebanking.util.PKI;
import com.noblebanking.util.TransactionRowMapper;
import com.noblebanking.util.UserRowMapper;

public class AdminDao implements AdminInterface {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TransactionRowMapper transactionRowMapper;
	
	@Autowired
	private InternalUserRowMapper internalUserRowMapper;
	
	@Autowired
	private UserRowMapper userRowMapper;
	
	@Autowired
	private IDRowMapper iDRowMapper;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	public void setiDRowMapper(IDRowMapper iDRowMapper) 
	{
        this.iDRowMapper = iDRowMapper;
	}
	
	public void setUserRowMapper(UserRowMapper userRowMapper) 
	{
        this.userRowMapper = userRowMapper;
	}
	
	public void setInternalUserRowMapper(InternalUserRowMapper internalUserRowMapper) 
	{
        this.internalUserRowMapper = internalUserRowMapper;
	}
	
	public void setTransactionRowMapper(TransactionRowMapper transactionRowMapper) 
	{
        this.transactionRowMapper = transactionRowMapper;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) 
	{
        this.jdbcTemplate = jdbcTemplate;
	}

	public  List<TransactionBean> retrieveCriticalTransactions() {
		List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
		try {
			String sql = "select * from transaction where TRANTYPE = ? and STATUS = ?";
			criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "CRITICAL", 0);
			return criticalTransactionsList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return criticalTransactionsList;
	}

	public Map<String, Double> retrieveUserBalances(List<TransactionBean> list) {
		Map<String, Double> userBalance = new HashMap<String, Double>();
		try {
			for(TransactionBean transactionBean : list)
				{
					String sql = "select balance from account where USER_ID = ?";
					double balance = jdbcTemplate.queryForObject(sql, Double.class, transactionBean.getFromUid());
					userBalance.put(transactionBean.getFromUid(), balance);
				}
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userBalance;
	}
	
	public void processCriticalTransactions(TransactionBean transactionBean) {
		try {
			    int temp=0;
				if(transactionBean.getAcceptstatus().equals("1"))
					{
						String query = "select balance from account where USER_ID=?";
						double actualBalance = this.jdbcTemplate.queryForObject(
						query, Double.class, transactionBean.getFromUid());
						
						if(actualBalance>=transactionBean.getAmount())
						{
						String update_balance = "UPDATE account SET balance =? where USER_ID = ?";
						jdbcTemplate.update(update_balance, new Object[] { actualBalance-transactionBean.getAmount(),
								transactionBean.getFromUid()});
						}
						else
						{
							temp=1;
						}
					}	
				if(temp==0)
				{
					String sql = "update transaction SET STATUS = ? WHERE TRANID = ?";
					jdbcTemplate.update(sql, new Object[] {1, transactionBean.getTranId()});
				}
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}

	public String addUser(UserBean userBean) {
		String message="Sorry, user can't be added right now";
		try {
			OTP otp=new OTP();// chetan change
			String pass=otp.generate();
			String myusername="coveringarray@gmail.com";
			String mypassword="tempPassword";
			GmailSmtpSSL email = new GmailSmtpSSL(myusername, mypassword);

			String subject = pass;
			String body = "";
			email.sendMailTo(userBean.getEmail(), subject, body);

			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();   
			String hashedPassword = passwordEncoder.encode(pass);  // chetan change
			
			String sql_externalcustomer = "INSERT INTO internalcustomer(EMPNAME, MOBILE, ADDRESS, EMAIL) values(?,?,?,?)";
			
			jdbcTemplate.update(sql_externalcustomer, new Object[] { userBean.getFirstname(),
					 userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail()});
			
			String query = "select empid from internalcustomer where EMPNAME=? and MOBILE =?";
			int empId = this.jdbcTemplate.queryForObject(
			query, Integer.class, userBean.getFirstname(), userBean.getMobile() );
			
			String sql_login = "INSERT INTO login(username, password, role, enabled ) values(?,?,?,?)";
			jdbcTemplate.update(sql_login, new Object[] { empId,
					hashedPassword, "ROLE_EMP", 1});
				message = "User added successfully and employee id is "+empId;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public String addExternalUser(UserBean userBean) {
		String message="Sorry, user can't be added right now/Transaction failed/ User already exists";
		try {
			
			OTP otp=new OTP();// chetan change
			String pass=otp.generate();
			String myusername="coveringarray@gmail.com";
			String mypassword="tempPassword";
			GmailSmtpSSL email = new GmailSmtpSSL(myusername, mypassword);

			String subject = pass;
			String body = "";
			email.sendMailTo(userBean.getEmail(), subject, body);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();   
			String hashedPassword = passwordEncoder.encode(pass);  // chetan change
			
			//ADDED FOR PKI BY SWETHA     inserting the keys part
			PKI pk=new PKI();
			
			String key_pairs[] = pk.generateKeys();
			OTP cert=new OTP();
			String certificate=cert.generate();
			//String certificate="hardcodingcertificate";
			String certificatecritical=cert.generate();
			
			String sql_externalcustomer = "INSERT INTO externalcustomer(FIRSTNAME, LASTNAME, MOBILE, ADDRESS, EMAIL,PUBKEY,PRIVKEY,CERTIFICATE,CERTIFICATECRITICAL) values(?,?,?,?,?,?,?,?,?)";
			
			jdbcTemplate.update(sql_externalcustomer, new Object[] {userBean.getFirstname(),userBean.getLastname(),
					 userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail(),key_pairs[1],key_pairs[0],certificate,certificatecritical});
			
			String query1 = "select USERID from externalcustomer where FIRSTNAME=? and MOBILE =? and EMAIL = ? and ADDRESS=? and LASTNAME = ?";
			int userId = this.jdbcTemplate.queryForObject(
			query1, Integer.class, userBean.getFirstname(), userBean.getMobile(), userBean.getEmail(), userBean.getAddress(), userBean.getLastname());
			
			
			
            String sql_bank = "INSERT INTO bank(USERID,PUBKEY) values(?,?)";
			
			jdbcTemplate.update(sql_bank, new Object[] {userId,key_pairs[1]});
            
			//COMMENTED FOR PKI BY SWETHA
			/*String sql_externalcustomer = "INSERT INTO externalcustomer(FIRSTNAME, LASTNAME, MOBILE, ADDRESS, EMAIL) values(?,?,?,?,?)";
			
			jdbcTemplate.update(sql_externalcustomer, new Object[] {userBean.getFirstname(),userBean.getLastname(),
					 userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail()});*/
			
			
			
			String sql_cust = "INSERT INTO ACCOUNT (balance, OPENDATE, USER_ID) values(?,?,?)";
			jdbcTemplate.update(sql_cust, new Object[] {10,"date",userId});
			
			String sql_login = "INSERT INTO login(username, password, role, enabled ) values(?,?,?,?)";
			jdbcTemplate.update(sql_login, new Object[] { userId,
					hashedPassword, userBean.getRole(), 1});
			message = "User added successfully, user id is "+userId;
			
			logger.info("Succesfully added External User info");
			String insert_log = "INSERT INTO logger(log) values(?)";
			jdbcTemplate.update(insert_log, new Object[] {"INFO : com.noblebanking.action.UserController - Succesfully added External User info"});
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	public UserBean editUser(IDBean idBean) {
		UserBean ubean = new UserBean();
		try {
			
			int empId = Integer.parseInt(idBean.getEmpid());
			String sql = "select * from internalcustomer where empid = ?";
			ubean = (UserBean)jdbcTemplate.queryForObject(sql, new Object[] { empId }, new InternalUserRowMapper() );
			return ubean;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return ubean;
	}

	public String editUserUpdateFields(UserBean userBean) {
		String message="Sorry, user can't be edit right now";
		try {
			String sql_externalcustomer = "UPDATE internalcustomer SET EMPNAME = ?, MOBILE= ?, ADDRESS = ?, EMAIL = ? WHERE empid =? ";
			
			jdbcTemplate.update(sql_externalcustomer, new Object[] { userBean.getFirstname(),
					 userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail(), userBean.getEmpid()});

			message = "Successfully edited employee "+userBean.getEmpid()+" data";
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	public String deleteUser(IDBean iDBean) {
		String message="Sorry, user can't be deleted right now";
		try {
				int empid = Integer.parseInt(iDBean.getEmpid());
				String valid_user_to_delete = "select count(*) from internalcustomer where empid=?";
				int count = this.jdbcTemplate.queryForObject(
						valid_user_to_delete, Integer.class, empid);
			if(count==1)
			{	String login_del = "DELETE from login where username =?";
				jdbcTemplate.update(login_del, new Object[] {empid});
			
				String sql_externalcustomer = "DELETE from internalcustomer where empid =?";
				jdbcTemplate.update(sql_externalcustomer, new Object[] {empid});

				message = "Successfully deleted employee "+empid+" data";
				
				logger.info("Succesfully deleted Internal User info");
				String insert_log = "INSERT INTO logger(log) values(?)";
				jdbcTemplate.update(insert_log, new Object[] {"INFO : com.noblebanking.action.UserController - Succesfully deleted Internal User info"});
			}
			else
			{
				message = "Employee "+iDBean.getEmpid()+" not found";
			}
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	public String deleteExternalUser(IDBean iDBean) {
		String message="Sorry, user can't be deleted right now";
		try {
				int id = Integer.parseInt(iDBean.getEmpid());
				String valid_user_to_delete = "select count(*) from externalcustomer where USERID=?";
				int count = this.jdbcTemplate.queryForObject(
						valid_user_to_delete, Integer.class, id);
			if(count==1)
			{	String login_del = "DELETE from login where username = ?";
				jdbcTemplate.update(login_del, new Object[] {iDBean.getEmpid()});
			
				String sql_externalcustomer = "DELETE from externalcustomer where USERID =?";
				jdbcTemplate.update(sql_externalcustomer, new Object[] {id});

				message = "Successfully deleted user "+id+" data";
				
				logger.info("Succesfully deleted External User info");
				String insert_log = "INSERT INTO logger(log) values(?)";
				jdbcTemplate.update(insert_log, new Object[] {"INFO : com.noblebanking.action.UserController - Succesfully deleted External User info"});

			}
			else
			{
				message = "User "+id+" not found";
			}
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public  List<IDBean> retrieveLogs() {
		List<IDBean> userRequestList = new ArrayList<IDBean>();
		try {
			String sql = "select * from logger";
			userRequestList = jdbcTemplate.query(sql, new IDRowMapper());
			return userRequestList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userRequestList;
	}
	
	public  List<TransactionBean> retrieveUserRequests() {
		List<TransactionBean> userRequestList = new ArrayList<TransactionBean>();
		try {
			String sql = "select * from transaction where STATUS = ?";
			userRequestList = jdbcTemplate.query(sql, new TransactionRowMapper(), 0);
			return userRequestList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userRequestList;
	}

	public UserBean editExternalUser(IDBean idBean) {
		UserBean ubean = new UserBean();
		try {
			int empId = Integer.parseInt(idBean.getEmpid());
			
			String sql = "select count(*) from transaction where FROMUID = ? and STATUS = ?";
			int count = this.jdbcTemplate.queryForObject(sql, Integer.class,empId, "5");
			
			if(count==1)
			{
			String sql_query = "select * from externalcustomer where USERID = ?";
			ubean = (UserBean)jdbcTemplate.queryForObject(sql_query, new Object[] { empId }, new UserRowMapper() );
			return ubean;
			}
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return ubean;
	}

	public String editExternalUserUpdateFields(UserBean userBean) {
		String message="Sorry, user can't be edited right now";
		try {
			String sql_externalcustomer = "UPDATE externalcustomer SET FIRSTNAME = ?, MOBILE= ?, ADDRESS = ?, EMAIL = ?, LASTNAME = ? WHERE USERID =? ";
			System.out.println("SEE THIS"+ userBean.getFirstname()+ userBean.getMobile()+userBean.getAddress()+userBean.getEmail()+userBean.getLastname());
			jdbcTemplate.update(sql_externalcustomer, new Object[] { userBean.getFirstname(),
					 userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail(),userBean.getLastname(), userBean.getEmpid()});
			
			String sql_updateCount = "UPDATE transaction SET STATUS = ? WHERE FROMUID =? and TRANTYPE = ?";
			
			jdbcTemplate.update(sql_updateCount, new Object[] {"6",userBean.getEmpid(),"PROFILE"});

			message = "Successfully edited user "+userBean.getEmpid()+" data";
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	
	//modified swetha..................
		public  List<TransactionBean> retrievepaymenttransactions(String userid) {
			List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
			try {
				String u=userid;
				
				String sql = "select * from transaction where TRANTYPE = ? and STATUS = ? and EMP_ID=?";
				//xyxyxyxyxyx
				criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "payment", 8,530);
				
				return criticalTransactionsList;
				} 
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return criticalTransactionsList;
		}
		public Boolean verifyCritical(String userid)
	       {
	               System.out.println("inside verify");
	               String data = "";
	               String signed_data = "";
	               PKI p = new PKI();
	               
	               try {
	                       String sql_get_certificate = "select signedcertificatecritical from bank where userid = ?";
	                       String sql_get_publicKey =  "Select pubkey from bank where userid = ?";
	                       
	                       signed_data = jdbcTemplate.queryForObject(
	                                       sql_get_certificate,  String.class, userid);
	       
	                       System.out.println("inside verify");
	                       String public_key_string = jdbcTemplate.queryForObject(
	                                               sql_get_publicKey, String.class, userid);
	       
	                       PublicKey publicKey = p.getPublic(public_key_string);
	                       
	                       String dataquery="select certificatecritical from externalcustomer where userid=?";
	                       String justcertificate=jdbcTemplate.queryForObject(dataquery, String.class,userid);
	                       Boolean verified= p.verifySignature(justcertificate, signed_data, publicKey);
	               //String signedCertificate =         p.sign("data", signed_data , publicKey );
	                       return verified;

	                       } 
	               catch (Exception e) {
	                       System.out.println(e);
	                       e.printStackTrace();                
	               }
				return false;                
	       
	       }
		//ADDED SWETHA FOR PKI VERIFICATION
		public Boolean verify(String userid)
	       {
	               System.out.println("inside verify");
	               String data = "";
	               String signed_data = "";
	               PKI p = new PKI();
	               
	               try {
	                       String sql_get_certificate = "select signedcertificate from bank where userid = ?";
	                       String sql_get_publicKey =  "Select pubkey from bank where userid = ?";
	                       
	                       signed_data = jdbcTemplate.queryForObject(
	                                       sql_get_certificate,  String.class, userid);
	       
	                       System.out.println("inside verify");
	                       String public_key_string = jdbcTemplate.queryForObject(
	                                               sql_get_publicKey, String.class, userid);
	       
	                       PublicKey publicKey = p.getPublic(public_key_string);
	                       
	                       String dataquery="select certificate from externalcustomer where userid=?";
	                       String justcertificate=jdbcTemplate.queryForObject(dataquery, String.class,userid);
	                       Boolean verified= p.verifySignature(justcertificate, signed_data, publicKey);
	               //String signedCertificate =         p.sign("data", signed_data , publicKey );
	                       return verified;

	                       } 
	               catch (Exception e) {
	                       System.out.println(e);
	                       e.printStackTrace();                
	               }
				return false;                
	       
	       }
		
		public void processPaymentTransactions(TransactionBean transactionBean,
				String userid){
		//public void processPaymentTransactions(TransactionBean transactionBean,String userid) {
			try {
				
				String fromAccountNumbernew = "select fromaccount from transaction where EMP_ID=? and tranid= ?";
				BigInteger fromnew = this.jdbcTemplate.queryForObject(
						fromAccountNumbernew, BigInteger.class, userid,transactionBean.getTranId());
				String fromAccountnew = fromnew.toString();
				String getuseridnew="select user_id from account where accountnum=?";
				int useridforpkinew=this.jdbcTemplate.queryForObject(getuseridnew,Integer.class,fromnew);
				System.out.println("getting the userid for pki......."+useridforpkinew);
				String useridforpki=""+useridforpkinew;
				Boolean verifyres=verify(useridforpki);
				System.out.println("verified result in the dao......."+verifyres);
					if(transactionBean.getAcceptstatus().equals("1"))
						{
						
						String fromAccountNumber = "select fromaccount from transaction where EMP_ID=? and tranid= ?";
						BigInteger from = this.jdbcTemplate.queryForObject(
								fromAccountNumber, BigInteger.class, userid,transactionBean.getTranId());
						String fromAccount = from.toString();
						System.out.println("able to get from account"+fromAccount);
						String sql = "select balance from account where accountnum = ?";
						double frombalance = jdbcTemplate.queryForObject(sql, Double.class, fromAccount);
						System.out.println("got the user balance...."+frombalance);
						//String sql = "select * from transaction where TRANTYPE = ? and STATUS = ? and TOACCOUNT=?";
						//criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "payment", 0,fromAccount);
						String toAccountNumber = "select toaccount from transaction where EMP_ID=? and tranid=?";
						BigInteger to = this.jdbcTemplate.queryForObject(
								toAccountNumber, BigInteger.class, userid,transactionBean.getTranId());
						String toAccount = to.toString();
						System.out.println("able to get to account"+toAccount);
						String sql2 = "select balance from account where accountnum = ?";
						double tobalance= jdbcTemplate.queryForObject(sql, Double.class, toAccount);
						System.out.println("got the user balance...."+tobalance);
                        
						//return criticalTransactionsList;
						//xyzxyzxyzxyzxyz
						if(frombalance>transactionBean.getAmount())
						{
						String update_frombalance = "UPDATE account SET balance =? where ACCOUNTNUM = ?";
						jdbcTemplate.update(update_frombalance, new Object[] { frombalance-transactionBean.getAmount(),
								fromAccount});
						
						String update_tobalance = "UPDATE account SET balance =? where ACCOUNTNUM = ?";
						jdbcTemplate.update(update_tobalance, new Object[] { tobalance+transactionBean.getAmount(),
								toAccount});
							
							
							String update_status = "UPDATE transaction SET status =? where  tranid= ?";
							jdbcTemplate.update(update_status, new Object[] { 1,
									transactionBean.getTranId()});
						}	
						
						//String sql = "update transaction SET STATUS = ? WHERE TRANID = ?";
						//jdbcTemplate.update(sql, new Object[] {1, transactionBean.getTranId()});
					//xyzxyzxyzxyzxyz
								} 
					else
					{
						String update_status = "UPDATE transaction SET status =? where  tranid= ?";
						jdbcTemplate.update(update_status, new Object[] { 9,
								transactionBean.getTranId()});
					}	
			}
			//adding for reject case of admin
			
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}		
		}
		
	
}
