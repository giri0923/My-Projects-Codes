package com.noblebanking.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TransferBean {
	
	@Pattern(regexp="^[0-9]{1,6}$", message="Enter valid  1 to 6 digits only")
	@NotNull(message = "Please fill the field")
	private String toAccount;

	@Pattern(regexp="^[0-9]{1,6}$", message="Enter valid  1 to 6 digits only")
	@NotNull(message = "Please fill the field")
	private String balance;
	
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
}