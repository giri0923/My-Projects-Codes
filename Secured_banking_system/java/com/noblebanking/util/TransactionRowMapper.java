package com.noblebanking.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.UserBean;

public class TransactionRowMapper implements RowMapper<TransactionBean> {

	TransactionBean transactionBean;
	
	@Override
	public TransactionBean mapRow(ResultSet rs, int rownum) throws SQLException {

		TransactionBean transactionBean= new TransactionBean();
		
		transactionBean.setAmount(rs.getDouble("AMOUNT"));
		transactionBean.setAuthorization(rs.getInt("AUTHORIZATION"));
		transactionBean.setEmpId(rs.getString("EMP_ID"));
		transactionBean.setFromAccount(rs.getDouble("FROMACCOUNT"));
		transactionBean.setFromUid(rs.getString("FROMUID"));
		transactionBean.setStatus(rs.getString("STATUS"));
		transactionBean.setToAccount(rs.getDouble("TOACCOUNT"));
		transactionBean.setTranId(rs.getInt("TRANID"));
		transactionBean.setTranType(rs.getString("TRANTYPE"));
		
		return transactionBean;
	
	}

}
