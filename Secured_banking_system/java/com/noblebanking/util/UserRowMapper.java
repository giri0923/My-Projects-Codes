package com.noblebanking.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.noblebanking.bean.UserBean;


public class UserRowMapper implements RowMapper<UserBean> {

	UserBean userBean;
	@Override
	public UserBean mapRow(ResultSet rs, int rownum) throws SQLException {

		userBean = new UserBean();
		
		userBean.setFirstname(rs.getString("firstName"));
		userBean.setEmail(rs.getString("email"));
		userBean.setAddress(rs.getString("address"));
		userBean.setLastname(rs.getString("lastName"));
		userBean.setMobile(rs.getString("mobile"));
		userBean.setEmpid((rs.getInt("USERID")));		
		return userBean;
	
	}

}
