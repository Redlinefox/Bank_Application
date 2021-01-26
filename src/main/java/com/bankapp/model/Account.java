package com.bankapp.model;

public class Account {

	private Integer accountID;
	private String accountType;
	private String balance;
	private String approved;
	private Integer ownerID;
	
	
	public Account() {}
	
	public Account(String accountType, String balance) {
		this.accountType = accountType;
		this.balance = balance;
	}
	
	public Account(Integer accountID, String accountType, String balance, String approved) {
		this.accountID = accountID;
		this.accountType = accountType;
		this.balance = balance;
		this.approved = approved;
	}
	
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public Integer getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}

}
