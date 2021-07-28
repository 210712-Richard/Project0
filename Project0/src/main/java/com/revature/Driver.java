package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.UserController;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		// Set up Jackson to serialize LocalDates and LocalDateTimes
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);
		
		// Starts the Javalin Framework
		Javalin app = Javalin.create().start(8080);
		
		UserController uc = new UserController();
		
		// As a user, I can log in.
		app.post("/user/login", uc::login);
		// As a user, I can register for a user account.
		app.post("/user/register", uc::register);
		// As a user, I can log out.
		app.delete("/user/logout", uc::logout);
		// As a user, I can get my current balance.
		app.get("/user/:username/balance", uc::getBalance);
		// As a user, I can deposit funds.
		app.put("/user/:username/deposit", uc::deposit);
		// As a user, I can withdraw funds.
		app.put("/user/:username/withdraw", uc::withdraw);
		// As a user, I can apply for a loan.
		app.put("/user/:username/applyForLoan", uc::applyLoan);
		// As a user, I can cancel an existing loan request.
		app.put("/user/:username/cancelLoan", uc::cancelLoan);
		// As a user, I can make payments on an outstanding loan (payments will be made by deducting funds from account)
		app.put("/user/:username/payLoan", uc::payLoan);
		
		// As an Admin, I can do all of the things that a user can except register
		// Additionally, as an Admin, I can approve pending loans
		app.put("/user/:username/:userDecision/approveLoan", uc::approveLoan);
		// Additionally, as an Admin, I can approve pending loans
		app.put("/user/:username/:userDecision/denyLoan", uc::denyLoan);

		
	}
}