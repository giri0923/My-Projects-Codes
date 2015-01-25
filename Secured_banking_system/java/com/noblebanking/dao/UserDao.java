package com.noblebanking.dao;

import java.math.BigInteger;
import java.security.PrivateKey;
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
import com.noblebanking.bean.Login;
import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.TransferBean;
import com.noblebanking.bean.UserBean;
import com.noblebanking.util.GmailSmtpSSL;
import com.noblebanking.util.OTP;
import com.noblebanking.util.PKI;
import com.noblebanking.util.TransactionRowMapper;
import com.noblebanking.util.UserRowMapper;

public class UserDao  implements UserInterface {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private UserRowMapper userRowMapper;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) 
	{
        this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setUserRowMapper(UserRowMapper userRowMapper) 
	{
        this.userRowMapper = userRowMapper;
	}
	
	public void addUser(UserBean userBean) {
		try {
			String sql_externalcustomer = "INSERT INTO externalcustomer(FIRSTNAME, LASTNAME, MOBILE, ADDRESS, EMAIL, USERID ) values(?,?,?,?,?,?,?)";
			String sql_login = "INSERT INTO login(username, password, role, enabled) values(?,?,?,?)";
			String sql_account = "INSERT INTO account(balance, opendate, closedate, user_id ) values(?,?,?,?)";
			jdbcTemplate.update(sql_externalcustomer, new Object[] { userBean.getFirstname(),
					userBean.getLastname(), userBean.getMobile(), userBean.getAddress(),
					userBean.getEmail(), userBean.getUsername()});
			jdbcTemplate.update(sql_login, new Object[] { userBean.getUsername(),
					userBean.getPassword(), "ROLE_USER", 1});
			jdbcTemplate.update(sql_account, new Object[] { "100.0","20-06-2014","22-06-2014",userBean.getUsername(),
					});
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public  List<TransactionBean> retrievetransactions(String user_id) {
		List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
		try {
			String sql = "select * from transaction where FROMUID = ? and STATUS = ?";
			criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(),user_id, 1);
			return criticalTransactionsList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return criticalTransactionsList;
	}

	@Override
	public String transferFunds(TransferBean transferBean,String user_id) {
		String returnString = "Transaction failed";
		try {
			String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
					checkAccountNumber, BigInteger.class, user_id);
			String fromAccount = ACCOUNTNUM.toString();
			String toAccount = transferBean.getToAccount();
			
			String touserid = "select user_id from account where accountnum=?";
			String touser = this.jdbcTemplate.queryForObject(
					touserid, String.class, toAccount);
			

			
			String fromrolecheck = "select ROLE from login where username=?";
			String fromrole = this.jdbcTemplate.queryForObject(
					fromrolecheck, String.class, user_id);
			
			String torolecheck = "select ROLE from login where username=?";
			String torole = this.jdbcTemplate.queryForObject(
					torolecheck, String.class, touser);

			
			if(fromrole.equals("ROLE_USER")&&torole.equals("ROLE_MER"))
				{returnString = "You are an User!!You cannot transfer to merchant!!SORRY";
			return returnString;
				}
			if(!fromAccount.equals(toAccount))
			{
			String query = "select balance from account where USER_ID=?";
			double actualBalance = this.jdbcTemplate.queryForObject(
					query, Double.class, user_id);
			
			if (actualBalance>=Double.parseDouble(transferBean.getBalance()))
				{
					String sql = "select count(*) from account where ACCOUNTNUM=?";
					int count = this.jdbcTemplate.queryForObject(
							sql, Integer.class,Integer.parseInt(transferBean.getToAccount()));

					if(count==1)
					{
						String query1 = "select balance from account where accountnum=?";
						double actualBalance1 = this.jdbcTemplate.queryForObject(
								query1, Double.class, toAccount);
						
						String debitBalance = "UPDATE account SET balance =? where USER_ID = ?";
						jdbcTemplate.update(debitBalance, new Object[] { actualBalance-Double.parseDouble(transferBean.getBalance()),
								user_id});
						String creditBalance = "UPDATE account SET balance =? where ACCOUNTNUM=?";
						jdbcTemplate.update(creditBalance, new Object[] { actualBalance1+Double.parseDouble(transferBean.getBalance()),
								Integer.parseInt(transferBean.getToAccount())});
						
						String sql2 = "INSERT INTO TRANSACTION( TRANTYPE, AMOUNT, STATUS, FROMUID,FROMACCOUNT,TOACCOUNT) values(?,?,?,?,?,?)";
						jdbcTemplate.update(sql2, new Object[] {"transfer",Double.parseDouble(transferBean.getBalance()),"1",user_id,Double.parseDouble(fromAccount),Double.parseDouble(toAccount)});

						returnString = "Successfully transfered";
						
						logger.info("trasferred funds transaction success");
						String insert_log = "INSERT INTO logger(log) values(?)";
						jdbcTemplate.update(insert_log, new Object[] {"INFO : com.noblebanking.action.UserController - trasferred funds transaction success"});

					}
					else
					{
						returnString = "Account Number doen't exists";
					}
				}
			else
			{
				returnString = "Sorry, No enough balance in your account";
			}
			}
			else
			{
				returnString = "Amount can't be transfered from your account to your account";
			}
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return returnString;
	}
	@Override
	public String checkLoginDetails(Login login) {
		String role = null;
		try {
			String sql = "select role from login where username=? and password=?";
			role = this.jdbcTemplate.queryForObject(
					sql, String.class, login.getUserId(),login.getPassword());
			return role;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public String creditFunds(IDBean idbean, String user_id) {
		String returnString = "Sorry, Transaction failed";
		try {
					String query = "select balance from account where USER_ID=?";
					double actualBalance = this.jdbcTemplate.queryForObject(
					query, Double.class, user_id);
				//	int g=transferBean.getToAccount();
					//System.out.println("printing 1........"+transferBean.getBalance());
					
					String sql = "UPDATE account SET balance =? where USER_ID = ?";
					jdbcTemplate.update(sql, new Object[] { actualBalance+Integer.parseInt(idbean.getEmpid()),
								user_id});
					BigInteger big;
					String sql1 = "select accountnum from account where user_id=?";
					big = this.jdbcTemplate.queryForObject(
							sql1, BigInteger.class, user_id);
					String sql2 = "INSERT INTO TRANSACTION( TRANTYPE, AMOUNT, STATUS, FROMUID,FROMACCOUNT) values(?,?,?,?,?)";
					jdbcTemplate.update(sql2, new Object[] { "credit",idbean.getEmpid(),"1",user_id,big});

					returnString = "Successfully credited amount";
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return returnString;
	}

	@Override
	public String debitFunds(IDBean idbean, String user_id) {
		String returnString = "Sorry, Transaction failed";
		try {
					String query = "select balance from account where USER_ID=?";
					double actualBalance = this.jdbcTemplate.queryForObject(
					query, Double.class, user_id);
					//System.out.println("amt"+Integer.parseInt(idbean.getEmpid()));
					if(actualBalance>=Integer.parseInt(idbean.getEmpid()))
					{
					String sql = "UPDATE account SET balance =? where USER_ID = ?";
					jdbcTemplate.update(sql, new Object[] { actualBalance-Integer.parseInt(idbean.getEmpid()),
								user_id});
					BigInteger big;
					String sql1 = "select accountnum from account where user_id=?";
					big = this.jdbcTemplate.queryForObject(
							sql1, BigInteger.class, user_id);
					String sql2 = "INSERT INTO TRANSACTION( TRANTYPE, AMOUNT, STATUS, FROMUID,FROMACCOUNT) values(?,?,?,?,?)";
					jdbcTemplate.update(sql2, new Object[] { "debit",Integer.parseInt(idbean.getEmpid()),"1",user_id,big});

				
					returnString = "Successfully debited amount";
					
					logger.info("Successfully debited amount from the user account");
					String insert_log = "INSERT INTO logger(log) values(?)";
					jdbcTemplate.update(insert_log, new Object[] {"INFO : com.noblebanking.action.UserController - Successfully debited amount from the user account"});

					}
					else
					{
						returnString = "Sorry, Transaction failed - Insufficient Funds in the account";
					}
	
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return returnString;
	}


	@Override
	public double retrieveBalance(String userId) {
		double balance=0;
		try {
					String query = "select balance from account where USER_ID=?";
					balance = this.jdbcTemplate.queryForObject(
					query, Double.class, userId);
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return balance;
	}
	
	@Override
	public String retrieveAccountNumber(String userId) {
		BigInteger balance;
		String balanceStr=null;
		try {
					String query = "select ACCOUNTNUM from account where USER_ID=?";
					balance = this.jdbcTemplate.queryForObject(
					query, BigInteger.class, userId);
					balanceStr = balance.toString();
					
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return balanceStr;
	}

	@Override
	public String debitFundsCriticalTransac(IDBean idbean,String user_id) {
		String message="Transaction failed. unable to debit";
		try {

			
			
			String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
					checkAccountNumber, BigInteger.class, user_id);

			String sql = "INSERT INTO TRANSACTION(FROMACCOUNT, TRANTYPE, AMOUNT, EMP_ID, STATUS, FROMUID) values(?,?,?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] { ACCOUNTNUM,"CRITICAL",Integer.parseInt(idbean.getEmpid()),"100","0",user_id});

			message = "This is a critical transaction; Your request has been sent for approval"; //new chetan modified code 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	@Override
	public String checkExistingUser(IDBean iDbean)
	{
		String message="Sorry request can't be processed..";
		try {
			String useremail=null;
			String sql = "select EMAIL from externalcustomer where USERID = ?";
			
			useremail = this.jdbcTemplate.queryForObject(sql, String.class,iDbean.getEmpid());
			
			if(useremail.length()>0)
			{
			String myusername="coveringarray@gmail.com";
			String mypassword="tempPassword";
			
			OTP otp = new OTP();
			String value = otp.generate();
			String body = "";
			GmailSmtpSSL email = new GmailSmtpSSL(myusername, mypassword);

			String subject = value;

			email.sendMailTo(useremail, subject, body);
			System.out.println("Done email");
			long current_time = System.currentTimeMillis();
			String start_time = String.valueOf(current_time);
			String end_time = String.valueOf(current_time+5*1000*60);
			
			
			String otp_sql = "select count(*) from OTP where ID = ?";
			
			int count = this.jdbcTemplate.queryForObject(otp_sql, Integer.class,iDbean.getEmpid());
			
			if(count==1)
			{
				String insertOTP = "Update OTP SET PASSKEY =? ,start_time= ?,end_time =? where ID =?";
				
				jdbcTemplate.update(insertOTP, new Object[] {value,start_time,end_time,iDbean.getEmpid()});
				
				message = "Your OTP is sent to mail";
			}
			else{
			String insertOTP = "INSERT INTO OTP(ID,PASSKEY,start_time,end_time) values(?,?,?,?)";
				
			jdbcTemplate.update(insertOTP, new Object[] {iDbean.getEmpid(),value,start_time,end_time});
			
			message = "Your OTP is sent to mail"; 
				}
			}
			else
			{
			message = "User Id not found, Enter valid user Id";
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String loginUsingOTP(Login login) {

		String message="Sorry request can't be processed..";
		try {
			String end_time=null;
			String sql = "select end_time from OTP where ID = ? and PASSKEY = ?";
			end_time = this.jdbcTemplate.queryForObject(sql, String.class,login.getUserId(),login.getPassword());
			
			if(end_time.isEmpty())
			{
				message = "Invalid User";
				
			}
			else
			{
				long current_time = System.currentTimeMillis();
				long end_temp = Long.parseLong(end_time);
				if(current_time<=end_temp)
				{
					message = "Success";
				}
				else
				{
					message = "OTP expired";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String changePassword(Login login) {

		String message="Sorry request can't be processed..";
		try {
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();   
			String hashedPassword = passwordEncoder.encode(login.getPassword());  // chetan change

			String sql = "UPDATE login SET password =? where username = ?";
			jdbcTemplate.update(sql, new Object[] { hashedPassword,
					login.getUserId()});
			
			message = "Successfully updated you can login now with new password";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	//ADDED   SWETHA retrieve the payment transactions from the table to display in the user
	
	public  List<TransactionBean> retrievepaymenttransactions(String userid) {
		List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
		try {
			String u=userid;
			System.out.println("inside dao......"+u);
			String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
					checkAccountNumber, BigInteger.class, u);
			String fromAccount = ACCOUNTNUM.toString();
			String sql = "select * from transaction where TRANTYPE = ? and STATUS = ? and FROMACCOUNT=?";
			criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "payment", 0,fromAccount);
			return criticalTransactionsList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return criticalTransactionsList;
	}
	
	//ADDED SWETHA  retrieve the accepted payments from the user on the merchant screen
	public  List<TransactionBean> retrieveAcceptedPayments(String userid) {
		List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
		try {
			String u=userid;
			System.out.println("inside dao......"+u);
			String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
					checkAccountNumber, BigInteger.class, u);
			String toAccount = ACCOUNTNUM.toString();
			String sql = "select * from transaction where TRANTYPE = ? and TOACCOUNT=?";
			criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "payment",toAccount);
			return criticalTransactionsList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return criticalTransactionsList;
	}

	//ADDED FOR PKI signing function
	public void signKey(String userid) {
		PKI p = new PKI();

		try {
		String sql_get_certificate = "Select certificate from externalcustomer where userid = ?";
		String sql_get_privateKey = "Select privkey from externalcustomer where userid = ?";

		//assert(userBean != null);
		//System.out.println("herE -- " + userBean.getEmpid());
		String certificate = jdbcTemplate.queryForObject(
		sql_get_certificate, String.class, userid);

		String private_key_string = jdbcTemplate.queryForObject(
		sql_get_privateKey, String.class, userid);

		System.out.println("priv "+private_key_string);
		PrivateKey privateKey = p.getPrivate(private_key_string);

		String signedCertificate = p.sign( certificate , privateKey );
		
		String sql = "UPDATE bank SET signedcertificate =? where userid = ?";
		jdbcTemplate.update(sql, new Object[] { signedCertificate,
				userid});
		
		} 
		catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
		}
	
	public void signKeyCritical(String userid) {
		PKI p = new PKI();

		try {
		String sql_get_certificate = "Select certificatecritical from externalcustomer where userid = ?";
		String sql_get_privateKey = "Select privkey from externalcustomer where userid = ?";

		//assert(userBean != null);
		//System.out.println("herE -- " + userBean.getEmpid());
		String certificate = jdbcTemplate.queryForObject(
		sql_get_certificate, String.class, userid);

		String private_key_string = jdbcTemplate.queryForObject(
		sql_get_privateKey, String.class, userid);

		PrivateKey privateKey = p.getPrivate(private_key_string);

		String signedCertificate = p.sign( certificate , privateKey );
		
		String sql = "UPDATE bank SET signedcertificatecritical =? where userid = ?";
		jdbcTemplate.update(sql, new Object[] { signedCertificate,
				userid});
		
		} 
		catch (Exception e) {
		e.printStackTrace();
		}
		}
	//ADDDED SWETHA USER ACCEPTS AND SETS THE STATUS AS 2
	public void processPaymentTransactions(TransactionBean transactionBean,String userid) {
		try {
				if(transactionBean.getAcceptstatus().equals("1"))
					{
						
					    signKey(userid);
						String update_status = "UPDATE transaction SET status =? where  tranid= ?";
						jdbcTemplate.update(update_status, new Object[] { 2,
								transactionBean.getTranId()});
					}	
				else 
				{
					String delete="update transaction set status=? where tranid=?";
					jdbcTemplate.update(delete,new Object[]{3,transactionBean.getTranId()});
				}
					//String sql = "update transaction SET STATUS = ? WHERE TRANID = ?";
					//jdbcTemplate.update(sql, new Object[] {1, transactionBean.getTranId()});
			} 
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
//added swetha FORWARD THE PAYMENT TO BANK BY CLICKING IN THE SUBMIT BUTTON
@Override
public  List<TransactionBean> forwardaccepttobank(TransactionBean transactionBean,String userid) {
List<TransactionBean> criticalTransactionsList = new ArrayList<TransactionBean>();
try {
	String u=userid;
	
	String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
	BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
			checkAccountNumber, BigInteger.class, u);
	String toAccount = ACCOUNTNUM.toString();
	//String sql = "select * from transaction where TRANTYPE = ? and STATUS = ? and TOACCOUNT=?";
	//criticalTransactionsList = jdbcTemplate.query(sql, new TransactionRowMapper(), "payment", 0,fromAccount);
	String sql = "UPDATE transaction SET status =? where TRANTYPE = ? and TOACCOUNT=? and tranid=?";
	//xyxyxyxyxyxyxyx
	jdbcTemplate.update(sql, new Object[] { 8,"payment",toAccount,transactionBean.getTranId()});
	return criticalTransactionsList;
	} 
catch (Exception e) {
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
		e.printStackTrace();
	}
	return userBalance;
}
}
