package com.noblebanking.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.noblebanking.bean.IDBean;

public class IDRowMapper implements RowMapper<IDBean>{

	IDBean iDBean;
	
	@Override
	public IDBean mapRow(ResultSet rs, int rownum) throws SQLException {

		IDBean iDBean= new IDBean();
		
		iDBean.setEmpid(rs.getString("log"));
		
		return iDBean;
	
	}

}
