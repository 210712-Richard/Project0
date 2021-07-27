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
		
		// Javalin has created a web server for us and we have
		// to tell Javalin how to handle the requests it receives.
		
		// app.METHOD("URN", CALLBACK_FUNCTION);
		// The Javalin CALLBACK_FUNCTION takes an argument ctx which 
		// represents the request and the response to the request.
		// ctx.body() - The body of the request
		// ctx.html() - Sends html as the response
		// ctx.status() - changes the status of the response
		app.get("/", (ctx)->ctx.html("Hello Woreld"));
		
		// object::method <- Reference to a method as a function we can pass to a method
		
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
		app.put("/user/:username/:userToApprove/approveLoan", uc::approveLoan);

		
	}
}