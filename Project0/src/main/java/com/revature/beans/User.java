package com.revature.beans;

import java.io.Serializable;

public class User implements Serializable{

	private String username;
	private String password;
	private double balance;
	private String type;
	private boolean pendingLoan;
	private double loanAmount;
	
	public User(String username, String password, double balance, String type) {
		super();
		this.type = type;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.pendingLoan = pendingLoan;
		this.loanAmount = loanAmount;
	}

	public boolean isPendingLoan() {
		return pendingLoan;
	}

	public void setPendingLoan(boolean pendingLoan) {
		this.pendingLoan = pendingLoan;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

