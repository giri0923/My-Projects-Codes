package com.noblebanking.bean;

public class TransactionBean {

	private int tranId;
	private double fromAccount;
	private double toAccount;
	private String tranType;
	private double amount;
	private double balance;
	private String empId;
	private String fromUid;
	private int authorization;
	private String status;
	private String acceptstatus;
	private int txnid;
	private String payer;
	
	public String getPayer(){
		return payer;
	}
	
	public void setPayer(String payer){
		this.payer=payer;
	}	
	public String getAcceptstatus() {
		return acceptstatus;
	}
	public void setAcceptstatus(String acceptstatus) {
		this.acceptstatus = acceptstatus;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getTranId() {
		return tranId;
	}
	public void setTranId(int tranId) {
		this.tranId = tranId;
	}
	public double getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(double fromAccount) {
		this.fromAccount = fromAccount;
	}
	public double getToAccount() {
		return toAccount;
	}
	public void setToAccount(double toAccount) {
		this.toAccount = toAccount;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getFromUid() {
		return fromUid;
	}
	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}
	public int getAuthorization() {
		return authorization;
	}
	public void setAuthorization(int authorization) {
		this.authorization = authorization;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int gettxnid() {
		return txnid;
	}
	public void settxnid(int txnid) {
		this.txnid = txnid;
	}
	
}