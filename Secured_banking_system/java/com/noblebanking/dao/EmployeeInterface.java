package com.noblebanking.dao;
import java.util.List;
import java.util.Map;

import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.UserBean;


public interface EmployeeInterface {
	//public List<TransactionBean> retrieveCriticalTransactions();
	public  List<TransactionBean> retrieveUserRequests(String empId); 
	public Map<String, Double> retrieveUserBalances(List<TransactionBean> list);
	//public void processCriticalTransactions(TransactionBean transactionBean);
	public String RequestModify(String userId);
	//public String editExternalUserUpdateFields(UserBean userBean);
	public String editExternalUserUpdateFields(UserBean userBean);
	public String RequestModifyTransaction2(String userId,String type,Double amount,int txnid);
}
