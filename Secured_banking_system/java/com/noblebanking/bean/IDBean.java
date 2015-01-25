package com.noblebanking.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class IDBean {
	
	@Pattern(regexp="^[0-9]{1,6}$", message="Enter valid 1 to 6 digits only")
	@NotNull(message = "Please fill the field")
	private String empid;

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}
}
