package com.noblebanking.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.noblebanking.bean.UserBean;

public class InternalUserRowMapper implements RowMapper<UserBean> {

	UserBean userBean;
	
	@Override
	public UserBean mapRow(ResultSet rs, int rownum) throws SQLException {

		userBean = new UserBean();
		
		userBean.setFirstname(rs.getString("empname"));
		userBean.setEmail(rs.getString("email"));
		userBean.setAddress(rs.getString("address"));
		userBean.setMobile(rs.getString("mobile"));
		userBean.setEmpid((rs.getInt("empid")));
		return userBean;

	}

}
