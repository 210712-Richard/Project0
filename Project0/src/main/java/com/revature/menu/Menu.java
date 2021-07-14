package com.revature.menu;

import java.util.Scanner;

public class Menu {

	public void start() {
		String username;
		String password;
		while(true) {
			switch(startMenu()) {
			case 1:
				System.out.println("Username:\n");
				Scanner scan = new Scanner(System.in);
				username = scan.nextLine();
				System.out.println("Password:\n");
				password = scan.nextLine();
				System.out.println(username + " " + password);

				
				break;
			case 2:
				System.out.println("Hi Admin");
				break;
			case 3:
				System.out.println("Goodbye!");
				break;
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
