package com.noblebanking.dao;

import java.util.List;
import java.util.Map;

import com.noblebanking.bean.TransactionBean;

public interface AdminInterface {

	public List<TransactionBean> retrieveCriticalTransactions();
	public Map<String, Double> retrieveUserBalances(List<TransactionBean> list);
	public Boolean verifyCritical(String userid);
	public void processCriticalTransactions(TransactionBean transactionBean);
}
