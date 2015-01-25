package com.noblebanking.dao;

import java.util.List;
import java.util.Map;

import com.noblebanking.bean.IDBean;
import com.noblebanking.bean.Login;
import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.TransferBean;
import com.noblebanking.bean.UserBean;

public interface UserInterface {
	
	public void addUser(UserBean userBean);
	public String checkLoginDetails(Login login);
	public String transferFunds(TransferBean transferBean, String user_id);
	public String creditFunds(IDBean idbean, String user_id);
	public String debitFunds(IDBean iDBean, String user_id);
	public double retrieveBalance(String userId);
	public String retrieveAccountNumber(String userId);
	public String debitFundsCriticalTransac(IDBean iDBean,
			String user_id);
	public String checkExistingUser(IDBean iDBean);
	public String loginUsingOTP(Login login);
	public String changePassword(Login login);
	public List<TransactionBean> retrievetransactions(String user_id);
	public List<TransactionBean> retrievepaymenttransactions(String user_id);
	public Map<String, Double> retrieveUserBalances(List<TransactionBean> list);
	public void processPaymentTransactions(TransactionBean transactionBean, String user_id);
	public List<TransactionBean> retrieveAcceptedPayments(String user_id);
	public  List<TransactionBean> forwardaccepttobank(TransactionBean transactionBean,String userid);
	public void signKeyCritical(String userid);
}
