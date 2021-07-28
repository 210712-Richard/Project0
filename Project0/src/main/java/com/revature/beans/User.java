package com.revature.beans;

import java.io.Serializable;

public class User implements Serializable{

	private String username;
	private String password;
	private double balance;
	private String type;
	private boolean pendingLoan = false;
	private double loanAmount = 0;
	private double withdrawAmount;
	private double depositAmount;
	private double amountDue;
	private double payment;
	private boolean outstandingLoan = false;
	private String userDecision;
	
	public User() {
		super();
	}
	public User(String username, String password, double balance, String type) {
		super();
		this.type = type;
		this.username = username;
		this.password = password;
		this.balance = balance;
	}
	
	
	
	public String getUserToApprove() {
		return userDecision;
	}
	public void setUserToApprove(String userDecision) {
		this.userDecision = userDecision;
	}
	public boolean isOutstandingLoan() {
		return outstandingLoan;
	}
	public void setOutstandingLoan(boolean outstandingLoan) {
		this.outstandingLoan = outstandingLoan;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
	public double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
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
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", balance=" + balance + ", type=" + type
				+ ", pendingLoan=" + pendingLoan + ", loanAmount=" + loanAmount + ", withdrawAmount=" + withdrawAmount
				+ ", depositAmount=" + depositAmount + ", amountDue=" + amountDue + ", payment=" + payment
				+ ", outstandingLoan=" + outstandingLoan + ", userToApprove=" + userDecision + "]";
	}
}

