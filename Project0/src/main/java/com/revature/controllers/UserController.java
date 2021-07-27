package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.services.UserService;
import com.revature.data.*;
import io.javalin.http.Context;

public class UserController {
	private static Logger log = LogManager.getLogger(UserController.class);
	private UserService us = new UserService();
	private UserDAO ud = new UserDAO();
	
	public void login(Context ctx) {
		log.trace("Login method called");
		log.debug(ctx.body());
		// Try to use a JSON Marshaller to create an object of this type.
		// Javalin does not come with a JSON Marshaller but prefers Jackson. You could also use GSON
		User u = ctx.bodyAsClass(User.class);
		log.debug(u);
		
		// Use the request data to obtain the data requested
		u = us.login(u.getUsername());
		log.debug(u);
		
		// Create a session if the login was successful
		if(u != null) {
			// Save the user object as loggedUser in the session
			ctx.sessionAttribute("loggedUser", u);
			
			// Try to use the JSON Marshaller to send a JSON string of this object back to the client
			ctx.json(u);
			return;
		}
		
		// Send a 401 is the login was not successful
		ctx.status(401);
	}
	
	public void register(Context ctx) {
		User u = ctx.bodyAsClass(User.class);

		if(us.checkAvailability(u.getUsername())) {
			User newUser = us.register(u.getUsername(), u.getPassword(), 0, "User");
			ctx.status(201);
			ctx.json(newUser);
		} else {
			ctx.status(409);
			ctx.html("Username already taken.");
		}
		
	}
	
	public void getBalance(Context ctx) {
		log.trace("getBalance method called");

		String username = ctx.pathParam("username");
		User loggedUser = (User) ctx.sessionAttribute("loggedUser");
		// if we aren't logged in or our username is different than the logged in username
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		ctx.html("Your current balance is " +loggedUser.getBalance());
//		if(loggedUser.getAmountDue() > 0){
//			ctx.html("With an outstanding loan payment of "+loggedUser.getAmountDue());
//		}
	}
	
	public void withdraw(Context ctx) {
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		double currentBalance = loggedUser.getBalance();
		User u = ctx.bodyAsClass(User.class);
		double withdrawAmount = u.getWithdrawAmount();
		if(withdrawAmount > currentBalance) {
			//Not enough funds to withdraw
			ctx.status(400);
			ctx.html("Not enough funds to withdraw.");
			return;
		}
		us.withdraw(loggedUser, u.getWithdrawAmount());
		ctx.html("Your new balance is " + loggedUser.getBalance());

	}
	
	public void deposit(Context ctx) {
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		User u = ctx.bodyAsClass(User.class);
		us.deposit(loggedUser, u.getDepositAmount());
		ctx.html("Your new balance is " + loggedUser.getBalance());
	}
	
	
	public void applyLoan(Context ctx) {
		log.trace("applyLoan method called");

		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		if(loggedUser.isPendingLoan()) {
			ctx.status(400);
			ctx.html("You already have a pending loan for $" + loggedUser.getLoanAmount() + ".");
			return;
		}
		if(loggedUser.isOutstandingLoan()) {
			ctx.status(400);
			ctx.html("You already have an outstanding loan for $" + loggedUser.getAmountDue() + ".");
			return;
		}
		User u = ctx.bodyAsClass(User.class);
		us.applyLoan(loggedUser, u.getLoanAmount());
		ctx.html("You now have a pending loan for "+ loggedUser.getLoanAmount());

	}
	
	public void cancelLoan(Context ctx) {
		log.trace("cancelLoan method called");

		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		if(!loggedUser.isPendingLoan()) {
			ctx.status(400);
			ctx.html("You do not currently have a pending loan.");
			return;
		}
		User u = ctx.bodyAsClass(User.class);
		us.cancelLoan(loggedUser);
		ctx.html("You have canceled your pending loan.");
		
	}
	
	public void payLoan(Context ctx) {
		String username = ctx.pathParam("username");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		
		User u = ctx.bodyAsClass(User.class);
		if(u.getPayment() > loggedUser.getBalance()) {
			ctx.html("You currently do not have enough funds in your account required to make this payment");
			return;
		}
		if(u.getPayment() > loggedUser.getAmountDue()) {
			ctx.html("Your current outstanding balance is only $"+ loggedUser.getAmountDue());
			return;
		}
		us.payLoan(loggedUser, u.getPayment());
		ctx.html("Your new outstanding balance is $" +loggedUser.getAmountDue());
	}
	
	public void approveLoan(Context ctx) {
		String username = ctx.pathParam("username");
		String userToApprove = ctx.pathParam("userToApprove");
		User loggedUser = (User)ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equalsIgnoreCase(username)) {
			ctx.status(403);
			return;
		}
		if(!loggedUser.getType().equals("Admin")) {
			ctx.status(403);
			return;
		}
		us.approveLoan(ud.getUser(userToApprove));
		ctx.html("The pending loan for " + userToApprove + " has been approved");
	}
	
	public void logout(Context ctx) {
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}
	
}
	
