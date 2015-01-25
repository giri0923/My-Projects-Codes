package com.noblebanking.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class Login {
	
	@NotEmpty(message = "Please enter your user id")
	@Pattern(regexp="^[0-9]{1,5}$", message="Enter valid digit number(1 to 5 digits)")
	private String userId;

	@NotEmpty(message = "Please fill the field")
	@Pattern(regexp="^[0-9a-zA-Z]{6}$", message="Enter valid digit number(6 digits)")
	private String password;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}