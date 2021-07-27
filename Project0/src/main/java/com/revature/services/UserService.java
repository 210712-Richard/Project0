package com.revature.services;

import com.revature.beans.User;
import com.revature.data.UserDAO;

public class UserService {
	public UserDAO ud = new UserDAO();
	
	
	public User login(User user) {
		User u = ud.getUser(user);
		return u;
	}
	
	
	public User register(String username, String password, double balance, String type) {
		User u = new User(username, password, balance, type);
		ud.addUser(u);
		ud.writeToFile();
		return u;
	}
	
	public void delete(String username, String password) {
		
	}
	
	public boolean checkAvailability(String newName) {
		return ud.getUsers()
				.stream()
				.noneMatch((u)->u.getUsername().equals(newName));
//		for(User u: ud.getUsers()) {
//			if(u.getUsername().equals(newName))
//				return false;
//		}
//		return true;
	}
	
	public void deposit(User u, double depositAmount) {
		u.setBalance(u.getBalance() + depositAmount);
		ud.updateUser(u);
	}
	
	public void withdraw(User u, double withdrawAmount) {
		u.setBalance(u.getBalance() - withdrawAmount);
		ud.updateUser(u);
	}

	public void applyLoan(User u, double loanAmount) {
		u.setLoanAmount(loanAmount);
		u.setPendingLoan(true);
		ud.updateUser(u);
	}
	
	public void cancelLoan(User u) {
		u.setLoanAmount(0);
		u.setPendingLoan(false);
		ud.updateUser(u);
	}
	
	public void approveLoan(User userToApprove) {
		User u = ud.getUser(userToApprove);
		u.setAmountDue(u.getLoanAmount());
		u.setBalance(u.getBalance() + u.getLoanAmount());
		u.setLoanAmount(0);
		u.setPendingLoan(false);
		u.setOutstandingLoan(true);
		ud.updateUser(u);
	}
	
	public void updateUser(User u) {
		ud.updateUser(u);
	}


	public void payLoan(User u, double payment) {
		u.setAmountDue(u.getAmountDue() - payment);
		u.setBalance(u.getBalance() - payment);
		if(u.getAmountDue() == 0) {
			u.setOutstandingLoan(false);
		}
		ud.updateUser(u);
	}
}
