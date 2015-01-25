package com.noblebanking.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.noblebanking.bean.Login;
import com.noblebanking.bean.TransactionBean;
import com.noblebanking.bean.TransferBean;
import com.noblebanking.bean.UserBean;
import com.noblebanking.util.TransactionRowMapper;
import com.noblebanking.util.UserRowMapper;

public class MerchantDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private UserRowMapper userRowMapper;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) 
	{
        this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setUserRowMapper(UserRowMapper userRowMapper) 
	{
        this.userRowMapper = userRowMapper;
	}
	//public List<TransactionBean> retrievetransactions(String user_id);
	

	

}
