package com.noblebanking.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.UserBean;
import com.noblebanking.util.InternalUserRowMapper;
import com.noblebanking.util.TransactionRowMapper;
import com.noblebanking.util.UserRowMapper;

public class EmployeeDao implements EmployeeInterface {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TransactionRowMapper transactionRowMapper;
	
	@Autowired
	private UserRowMapper userrowmapper;
	
	@Autowired
	private InternalUserRowMapper internalUserRowMapper;
	
	
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
	public String createUserTransaction(String UserId){
		String message="Cannot create, transaction failed";
		try
		{
		
			String query = "select balance from account where USER_ID=?";
			double actualBalance = this.jdbcTemplate.queryForObject(
			query, Double.class, UserId);
			System.out.println("bal"+actualBalance);
			String query1 = "select amount from transaction where fromuid=? and trantype=? and status=?";
			double newamount = this.jdbcTemplate.queryForObject(
			query1, Double.class, UserId,"create","0");
			System.out.println("amount"+newamount);
			
			String sql = "UPDATE account SET balance =? where USER_ID = ?";
			jdbcTemplate.update(sql, new Object[] { actualBalance+newamount,
						UserId});
			
			String crsql = "update transaction SET STATUS = ? WHERE fromuid= ? and trantype=? and status=?";
			jdbcTemplate.update(crsql, new Object[] {1, UserId,"create",0});
			
			message="Successfully created credit transaction for User "+UserId+ ",balance is updated new balance is "+(actualBalance+newamount);
			return message;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	
	public String modifyUserTransaction(String UserId){
		String message="Cannot modify";
		try
		{
			String tx="select txnid from transaction where trantype=? and fromuid=? and status=?";
			int txnid = this.jdbcTemplate.queryForObject(
					tx, Integer.class,"modify", UserId,"0");
			System.out.println("txnid inside modify........"+txnid);
			String amt = "select amount from transaction where trantype=? and fromuid=? and status=?";
			double amount = this.jdbcTemplate.queryForObject(
					amt, Double.class, "modify", UserId,"0");	
			//amount<
			String from = "select fromaccount from transaction where tranid=?";
			double fromaccount = this.jdbcTemplate.queryForObject(
					from, Double.class, txnid);
			
			String to = "select toaccount from transaction where tranid=?";
			double toaccount = this.jdbcTemplate.queryForObject(
					to, Double.class, txnid);
			
			
			String txnamount = "select amount from transaction where tranid=?";
			double txnamt = this.jdbcTemplate.queryForObject(
					txnamount, Double.class, txnid);
			if(amount>txnamt)
			{
				message="The amount"+amount+ "to be modified is greater than the transfer transaction amount "+txnamt+".Please enter a smaller amount and try again";
				return message;
			}
			
			String frombal="select balance from account where accountnum=?";
			double frombalance = this.jdbcTemplate.queryForObject(
					frombal, Double.class, fromaccount);
			
			String tobal="select balance from account where accountnum=?";
			double tobalance = this.jdbcTemplate.queryForObject(
					tobal, Double.class, toaccount);
			
			String fromupdate="UPDATE account SET balance =? where accountnum = ?";
			jdbcTemplate.update(fromupdate, new Object[] { frombalance+amount,
					fromaccount});
			
			String toupdate="UPDATE account SET balance =? where accountnum = ?";
			jdbcTemplate.update(toupdate, new Object[] { tobalance-amount,
					toaccount});
			
			String modsql = "update transaction SET STATUS = ? WHERE fromuid= ? and trantype=? and status=?";
			jdbcTemplate.update(modsql, new Object[] {1, UserId,"modify",0});
			
			
			message="Success modified the transfer transaction of user "+UserId;
			return message;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public String merchantprocesspayment(String userId,Integer txnid)
	{
		String message="Cannot modify";
		try
		{
			txnid=26;
			String tx = "select trantype from transaction where tranid=?";
			String txn = this.jdbcTemplate.queryForObject(
					tx, String.class, txnid);	
		
			if(txn.equals("payment"))
			{
			String amt = "select amount from transaction where trantype=? and emp_id=? and status=? and tranid=?";
			double amount = this.jdbcTemplate.queryForObject(
					amt, Double.class, "payment", userId,"0",26);	
		
			String from = "select fromaccount from transaction where trantype=? and emp_id=? and status=? and tranid=?";
			double fromaccount = this.jdbcTemplate.queryForObject(
					from, Double.class, "payment", userId,"0",26);
			
			String to = "select toaccount from transaction where trantype=? and emp_id=? and status=? and tranid=?";
			double toaccount = this.jdbcTemplate.queryForObject(
					to, Double.class, "payment", userId,"0",26);
			
			
			
			String frombal="select balance from account where accountnum=?";
			double frombalance = this.jdbcTemplate.queryForObject(
					frombal, Double.class, fromaccount);
			
			String tobal="select balance from account where accountnum=?";
			double tobalance = this.jdbcTemplate.queryForObject(
					tobal, Double.class, toaccount);
			
			String fromupdate="UPDATE account SET balance =? where accountnum = ?";
			jdbcTemplate.update(fromupdate, new Object[] { frombalance-amount,
					fromaccount});
			
			String toupdate="UPDATE account SET balance =? where accountnum = ?";
			jdbcTemplate.update(toupdate, new Object[] { tobalance+amount,
					toaccount});
			
			String modsql = "update transaction SET STATUS = ? WHERE trantype= ? and  status=?";
			jdbcTemplate.update(modsql, new Object[] {1, "payment",0});
			
			
			message="success";
			return message;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public String submitpaymentrequest(String UserId,String curuserid,Double amount){
		String message="Cannot place payment request please check user id";
		try
		{
			System.out.println("curuserid"+curuserid);
			System.out.println("UserId"+UserId);
			//UserId="101";
			String from = "select accountnum from account where user_id=?";
			double useraccount = this.jdbcTemplate.queryForObject(
					from, Double.class, UserId);
			String to = "select accountnum from account where user_id=?";
			double merchantaccount = this.jdbcTemplate.queryForObject(
					to, Double.class, curuserid);
			String ct="select count(*) from transaction where fromuid=? and TRANTYPE=? and status=? and fromaccount=?";					
			Integer sql2 = jdbcTemplate.queryForObject(ct, Integer.class,curuserid,"payment","0",useraccount);
			
			if(sql2==0){
				
			
			String sql = "INSERT INTO TRANSACTION(TRANTYPE,FROMACCOUNT,TOACCOUNT,AMOUNT,EMP_ID,FROMUID,STATUS) values(?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] {"payment",useraccount,merchantaccount,amount,"530",curuserid,"0"});
			message="A payment request was placed";
			return message;
			}		
			else
			{
			   	String messagefalse="Already requested the payment for this user "+UserId+" which is not yet processed";
			   	return messagefalse;
			   	
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	
	public String deleteUserTransaction(String UserId){
		String message="Cannot delete";
		try
		{
			String deletesql = "DELETE FROM transaction where fromuid=? and trantype=? and status=? order by RAND() desc limit 1";
			jdbcTemplate.update(deletesql, new Object[] { UserId,"debit","0"});
			
			String delsql = "update transaction SET STATUS = ? WHERE fromuid= ? and trantype=? and status=?";
			jdbcTemplate.update(delsql, new Object[] {1, UserId,"delete",0});
			
			message="Successfully deleted a debit transaction of user "+UserId;
			return message;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	public UserBean editexternalUser(String userid) {
		UserBean ubean = new UserBean();
		//ubean.setUsername(userid);
		try {
			String sql = "select * from externalcustomer where userid = ?";
			//ubean = (UserBean)jdbcTemplate.queryForObject(sql, userid, new UserRowMapper() );
			//ubean = jdbcTemplate.query(sql, new UserRowMapper(),userid);
			//ubean = (UserBean)jdbcTemplate.queryForObject(sql, new Object[] { userid }, new UserRowMapper() );
		//	String firstname = jdbcTemplate.queryForObject(sql, String.class, "101");
			//tring sql1 = "select mobile from externalcustomer where userid = ?";
			//String mobile = jdbcTemplate.queryForObject(sql, String.class, "101");
//			System.out.println("IN DAO"+firstname+ " "+ userid);
//			ubean.setFirstname(firstname);
//			ubean.setMobile(mobile);
			
			ubean = (UserBean)jdbcTemplate.queryForObject(sql, new Object[] { userid }, new UserRowMapper() );
			//userBalance.put(transactionBean.getFromUid(), balance);
			ubean.setUsername(userid);
			return ubean;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return ubean;
	}
	//RequestModify
	public String RequestModify(String userId) {
		String message="Transaction failed..";
		try {
			
			//String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			//BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
				//	checkAccountNumber, BigInteger.class, user_id);

			String emp="select empid from internalcustomer order by RAND() limit 1";
			
		//	String emp1="select empid from internalcustomer order by RAND() limit 1;";
			Integer sql1 = jdbcTemplate.queryForObject(emp, Integer.class);
		//	System.out.println("EMPLLOYEE"+emp +" "+Integer.parseInt(emp));
			String ct="select count(*) from transaction where fromuid=? and TRANTYPE=? and status=?";					
			Integer sql2 = jdbcTemplate.queryForObject(ct, Integer.class,userId,"PROFILE",0);
			
			if(sql2==0)
			{
			String sql = "INSERT INTO TRANSACTION(TRANTYPE,EMP_ID,STATUS,FROMUID) values(?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] { "profile",sql1,"0",userId});
			}	
			else 
				return "false";
			//int userId=10;
			//int u=Math.random()
			
			

			message = "Your record has been inserted into transaction table"; 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public String RequestModifyTransaction(String userId,String type) {
		String message="Transaction failed..";
		try {
			String emp="select empid from internalcustomer order by RAND() limit 1";
			
			Integer sql1 = jdbcTemplate.queryForObject(emp, Integer.class);
			
			String ct="select count(*) from transaction where fromuid=? and TRANTYPE=? and status=?";					
			Integer sql2 = jdbcTemplate.queryForObject(ct, Integer.class,userId,type,"0");
			if(sql2==0)
			{
				if(type.equals("create")||type.equals("delete")||type.equals("modify"))
				{
					String sql = "INSERT INTO TRANSACTION(TRANTYPE,EMP_ID,STATUS,FROMUID) values(?,?,?,?)";
					jdbcTemplate.update(sql, new Object[] { type,sql1,"0",userId});
				}
				
			return "true";
			}
			else
				return "false";

		//	message = "Your record has been inserted into transaction table"; 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	public String RequestModifyTransaction1(String userId,String type,Double amount) {
		String message="Transaction failed..";
		try {
			

			String emp="select empid from internalcustomer order by RAND() limit 1;";
			
			Integer sql1 = jdbcTemplate.queryForObject(emp, Integer.class);
			
			String ct="select count(*) from transaction where fromuid=? and TRANTYPE=? and status=?";					
			Integer sql2 = jdbcTemplate.queryForObject(ct, Integer.class,userId,type,0);
			
			if(sql2==0)
			{
				if(type.equals("create")||type.equals("delete")||type.equals("modify"))
				{
					String sql = "INSERT INTO TRANSACTION(TRANTYPE,EMP_ID,STATUS,FROMUID,AMOUNT) values(?,?,?,?,?)";
					jdbcTemplate.update(sql, new Object[] { type,sql1,"0",userId,amount});
				}
				
				message =" Success!!!Request Placed to employee";
			}
			else
				return "false";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public String RequestModifyTransaction2(String userId,String type,Double amount,int txnid) {
		String message="Transaction failed..";
		try {
			
			//String checkAccountNumber = "select ACCOUNTNUM from account where USER_ID=?";
			//BigInteger ACCOUNTNUM = this.jdbcTemplate.queryForObject(
				//	checkAccountNumber, BigInteger.class, user_id);

			//String tran="select trantype from transaction where txnid=? and fromuid=?";					
			//String transfertype = jdbcTemplate.queryForObject(tran, String.class,txnid,userId);
			
			
			String emp="select empid from internalcustomer order by RAND() limit 1;";
			
		//	String emp1="select empid from internalcustomer order by RAND() limit 1;";
			Integer sql1 = jdbcTemplate.queryForObject(emp, Integer.class);
			
			String ct="select count(*) from transaction where fromuid=? and TRANTYPE=? and status=?";					
			Integer sql2 = jdbcTemplate.queryForObject(ct, Integer.class,userId,type,"0");
			
			if(sql2==0)
			{
				if(type.equals("create")||type.equals("delete")||type.equals("modify"))
				{
					String sql = "INSERT INTO TRANSACTION(TRANTYPE,EMP_ID,STATUS,FROMUID,AMOUNT,TXNID) values(?,?,?,?,?,?)";
					jdbcTemplate.update(sql, new Object[] { type,sql1,"0",userId,amount,txnid});
					
					 message="Success, A request has been sent to employee to modify transaction";
					}
			}
			else
				return "false";

		//	message = "Your record has been inserted into transaction table"; 
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	
	
	public  List<TransactionBean> retrieveUserRequests(String empId) {
		List<TransactionBean> userRequestList = new ArrayList<TransactionBean>();
		try {
			String sql = "select * from transaction where EMP_ID=? and STATUS = ?";
			userRequestList = jdbcTemplate.query(sql, new TransactionRowMapper(),empId, 0);
			return userRequestList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userRequestList;
	}
	
	public  List<TransactionBean> retrieveUserRequestsMerchant(String empId) {
		List<TransactionBean> userRequestList = new ArrayList<TransactionBean>();
		try {
			String sql = "select * from transaction where EMP_ID=? and STATUS = ?";
			userRequestList = jdbcTemplate.query(sql, new TransactionRowMapper(),empId, "0");
			return userRequestList;
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userRequestList;
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
	
	//editExternalUserUpdateFieldsz
	
	public String EmployeeProfileCheck(String userId,String empid)
	{
		String message="Sorry, user has edited already";
		try {
			String sql_check = "select count(*) from transaction where fromuid=? and trantype=? and emp_id=? and status=?";
			//Stri
			//String UserId=userBean.getUsername();
			
			
					
					
					int stat = jdbcTemplate.queryForObject(sql_check, Integer.class, userId,"profile",empid,"0");
			
					if(stat>0)
			message = "true";
					else
						message="false";
			return message;
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
		
	}
	public String EmployeeCreateCheck(String userId,String empid)
	{
		String message="Sorry, user has edited already";
		try {
			String sql_check = "select count(*) from transaction where fromuid=? and trantype=? and emp_id=? and status=? ";
			//Stri
			//String UserId=userBean.getUsername();
					Integer stat = jdbcTemplate.queryForObject(sql_check, Integer.class, userId,"create",empid,"0");
					if(stat>0)
			message = "true";
					else
						message="false";
			return message;
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
		
	}
	
	public String EmployeeModifyCheck(String userId,String empid)
	{
		String message="Sorry, user has edited already";
		try {
			String sql_check = "select count(*) from transaction where fromuid=? and trantype=? and emp_id=? and status=?";
					Integer stat = jdbcTemplate.queryForObject(sql_check, Integer.class, userId,"modify",empid,"0");
					if(stat>0)
			message = "true";
					else
						message="false";
			return message;
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
		
	}
	public String EmployeeDeleteCheck(String userId,String empid)
	{
		String message="Sorry, user has edited already";
		try {
			String sql_check = "select count(*) from transaction where fromuid=? and trantype=? and emp_id=? and status=?";
			//Stri
			//String UserId=userBean.getUsername();
		
					Integer stat = jdbcTemplate.queryForObject(sql_check, Integer.class, userId,"delete",empid,"0");
				
					if(stat>0)
			message = "true";
					else
						message="false";
			return message;
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
		
	}
	

	public String editExternalUserUpdateFields(UserBean userBean) {
		String message="Sorry, user can't edit right now";
		try {
			String sql_externalcustomer = "UPDATE externalcustomer SET  MOBILE= ?, ADDRESS = ? WHERE userid =? ";
			//Stri
			String UserId=userBean.getUsername();
			
			
			jdbcTemplate.update(sql_externalcustomer, new Object[] { userBean.getMobile(), userBean.getAddress(),UserId
					 
					});
			
			String sql = "update transaction SET STATUS = ? WHERE fromuid= ? and trantype=? and status=?";
			jdbcTemplate.update(sql, new Object[] {1, UserId,"profile",0});
			
			
	//	public oolean	
//System.out.println("hddd"+userBean.getUsername());
			message = "Successfully edited customer "+userBean.getUsername()+" data";
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	public String sendToAdmin(TransactionBean transactionBean) {
		String message="Sorry, request can't be processed";
		try {
			String sql_update = "UPDATE transaction SET  STATUS= ? WHERE tranid =? ";

			jdbcTemplate.update(sql_update, new Object[] {"5",transactionBean.getTranId()});
			
			message = "Successfully sent request to admin";
			
			} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	
	public String ModifyTransactionTxnCheck(String UserId,int txnid)
	{
		String message="Cannot modify";
		try
		{
			String uids = "select fromuid from transaction where tranid=? and trantype=?";
			String uid = this.jdbcTemplate.queryForObject(
					uids, String.class,txnid,"transfer");	
			//System.out.println("uid"+uid);
			if(uid.equals(null))
			{
				return "false";
			}
			if(uid.equals(UserId))
			{
				return "true";
			}
			else
				return "false";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

}
