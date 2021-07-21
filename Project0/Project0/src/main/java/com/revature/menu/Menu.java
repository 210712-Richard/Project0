package com.revature.menu;

import com.revature.data.*;
import com.revature.services.*;
import com.revature.beans.*;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
	
	private UserService us = new UserService();
	private User loggedUser = null;

	public void start() {
		String username;
		String password;
		Scanner scan = new Scanner(System.in);
		User u;
		mainloop: while(true) {
		mainswitch:	switch(startMenu()) {
			case 1:
				//login

				// user name
				do {
					System.out.println("Username:\n");
					username = scan.nextLine();
					if(username.equalsIgnoreCase("4")) {
						break mainswitch;
					}
					System.out.println("Password:\n");
					password = scan.nextLine();
					u = us.login(username);
					
					if(u == null) {
						System.out.println("Username not registered, please try again or enter 4 to return to the menu");
					}
					else{
						while(!password.equals(u.getPassword())){
							System.out.println("Invalid password, please try again or enter 4 to return to the menu");
							System.out.println("Password:\n");
							password = scan.nextLine();
							if(password.equalsIgnoreCase("4")) {
								break mainswitch;
							}
						}
					}
				} while (u == null);
				
				loggedUser = u;
				/*
				 * Do Account activities here
				 */
				System.out.println("Successfully logged in!");
				switch(loggedUser.getType()) {
				case "Customer":
					customer(loggedUser);
					break;
				case "Admin":
					admin(loggedUser);
					break;
				}
				

				break mainswitch;
					

			case 2:
				//register
				do {
					System.out.println("Please enter the username you would like to register with.\n");
					username = scan.nextLine();
					u = us.login(username);
					if(username.equalsIgnoreCase("4")) {
						break mainswitch;
					}
					if(u != null) {
						System.out.println("Username already in use, please try again or enter 4 to quit");
					}
					
				} while (u != null);
				System.out.println("Please enter your password:\n");
				password = scan.nextLine();
				us.register(username, password, 0, "Customer");
				System.out.println("Successfully registered! Your initial balance is 0");
				customer(u);
				
			case 3:
				//quit
				System.out.println("Goodbye!");
				return;
				
			default:
				System.out.println("Oops try again");
				break;
			}
		}	
			
	}
	
	
	private int adminMenu() {
		System.out.println("Welcome Admin! Please select the action you would like to do.");
		System.out.println("\t 1. Approve a loan");
		System.out.println("\t 2. Quit");	
		
		int x;
		Scanner scan = new Scanner(System.in);
		x = Integer.parseInt(scan.nextLine());
		
		return x;
		
	}
	
	private void admin(User u) {
		while(true) {
			int x = adminMenu();
			Scanner scan = new Scanner(System.in);
			double amount;
			
			switch(x) {
			case 1:
				// Loan Approval
				
			case 2:
				return;
			}
		}
		
		
	}

	private int customerMenu() {
		System.out.println("Welcome Customer! Please select the action you would like to do.");
		System.out.println("\t 1. Check Balance");
		System.out.println("\t 2. Apply for a loan");
		System.out.println("\t 3. Make a deposit");
		System.out.println("\t 4. Make a withdrawl");
		System.out.println("\t 5. Quit");	
		
		int x;
		Scanner scan = new Scanner(System.in);
		x = Integer.parseInt(scan.nextLine());
		return x;
		
	}

	private void customer(User u) {
		while(true) {
			int x = customerMenu();
			Scanner scan = new Scanner(System.in);
			double amount;
			switch(x) {
			case 1:
				System.out.println("Your current balance is $" + u.getBalance());
				break;
		
			case 2:
				if(u.isPendingLoan()) {
					System.out.println("You already have pending loan!");
					break;
				}
				// Loan Application
				System.out.println("How much would you like to borrow?");
				try{
					amount = scan.nextDouble();
					u.setPendingLoan(true);
					u.setLoanAmount(amount);
					us.updateUser(u);
					System.out.println("Okay! You have a pending loan for $" + amount);
	
				} finally {
				}
				break;
				
			case 3:
				// Deposit
				System.out.println("How much would you like to deposit?");
				try{
					amount = scan.nextDouble();
					u.setBalance(u.getBalance() + amount);
					us.updateUser(u);
					System.out.println("Okay! Your new balance is $" + u.getBalance());
	
				} finally {
				}
				break;
	
			case 4:
				// Withdraw
				System.out.println("How much would you like to withdraw?");
				try{
					amount = scan.nextDouble();
					if(amount > u.getBalance()) {
						System.out.println("Invalid amount. You only have $" + u.getBalance() + "to withdraw!");
						customer(u);
					}
					u.setBalance(u.getBalance() - amount);
					us.updateUser(u);
					System.out.println("Okay! Your new balance is $" + u.getBalance());
	
				} finally {
				}
				break;
				
			case 5:
				// Quit
				return;
				
				
			default:
				System.out.println("Oops try again");
				break;
			}
		}
	}
	private int startMenu() {
		System.out.println("Welcome! Please select the action you would like to do.");
		System.out.println("\t 1. Login");
		System.out.println("\t 2. Register");
		System.out.println("\t 3. Quit");

		int x;
		Scanner scan = new Scanner(System.in);
		x = Integer.parseInt(scan.nextLine());
		return x;

		
	}

	

}
