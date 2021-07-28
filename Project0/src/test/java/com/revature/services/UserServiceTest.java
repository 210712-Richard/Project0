package com.revature.services;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
//import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.User;
import com.revature.beans.UserType;
//import com.revature.beans.UserType;
import com.revature.data.UserDAO;

class UserServiceTest {

	private static UserService service;
	private static User u;
	
	@BeforeAll // Specifies that this static method will be run before any tests
	public static void setUpClass() {
		u = new User("Test","test12", 0, "Customer"); // New users start with a date of 1/1/21
	}
	
	@BeforeEach // Specifies a method that is to be run before each test
	public void setUpTests() {
		service = new UserService(); // create a new userService for every test to maximize isolation
		service.ud = Mockito.mock(UserDAO.class);
	}
	

	@Test
	public void testPayLoan() {
		double startingBalance = u.getBalance();
		double amountToPay = u.getPayment();
		double amountOwed = u.getAmountDue();
		service.payLoan(u, u.getPayment());
		assertEquals(startingBalance - amountToPay, u.getBalance(), "Asserting remaining balance after payment");
		assertEquals(amountOwed - amountToPay, u.getAmountDue(), "Asserting remaining amount due after payment");
	}
	
	public void testDeposit() {
		double startingBalance = u.getBalance();
		double amountToDeposit = u.getDepositAmount();
		service.deposit(u, u.getDepositAmount());
		assertEquals(startingBalance + amountToDeposit, u.getBalance(), "Asserting remaining balance after deposit");
	}
	
	public void testWithdraw() {
		double startingBalance = u.getBalance();
		double amountToWithdraw = u.getWithdrawAmount();
		service.deposit(u, u.getWithdrawAmount());
		assertEquals(startingBalance + amountToWithdraw, u.getBalance(), "Asserting remaining balance after withdrawl");
	}
	
//	@Test
//	public void testLogin() {
//		User newUser = service.register(u.getUsername(), u.getPassword(), 0, "Customer");
//		
//		User use = service.login(newUser);
//		
//		// An object that allows us to receive parameters from methods called on a Mockito mock object
//		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//
//		// ud.addUser() was called with our User as an argument.
//		Mockito.verify(service.ud).addUser(captor.capture());
//
//		// ud.writeToFile() was called.
//		Mockito.verify(service.ud).writeToFile();
//		if(use!=null) {
//			System.out.println(use.toString());
//
//		}
//		// authenticating test user by logging in
//		assertEquals(use != null, true ,"Authenticating user");		
//		
//	
//		service.login(u);
//		
//		// An object that allows us to receive parameters from methods called on a Mockito mock object
//		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//
//		// ud.addUser() was called with our User as an argument.
//		Mockito.verify(service.ud).addUser(captor.capture());
//
//		// ud.writeToFile() was called.
//		Mockito.verify(service.ud).writeToFile();
//		
//		// A user is created with the given arguments
//		// That user is of type Player
//		// That user has a starting Currency of 1000
//		User u = captor.getValue();
//		assertEquals("Customer", u.getType(), "Asserting it is a Customer");
//		assertEquals("Test", u.getUsername(), "Asserting username is correct");
//		assertEquals("test12", u.getPassword(), "Asserting password is correct");
//	}
//	
	@Test
	public void testFailLogin() {
		User testUser = new User("failUser", "Failed", 0, "Customer");
		User use = service.login(testUser);
		assertEquals(use == null, true ,"Failed to authenticate user");		

	}
	
	@Test
	public void testRegister() {
		String username = "test";
		String password = "test12";
		service.register(username, password, 0, "Customer");
		
		// An object that allows us to receive parameters from methods called on a Mockito mock object
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

		// ud.addUser() was called with our User as an argument.
		Mockito.verify(service.ud).addUser(captor.capture());

		// ud.writeToFile() was called.
		Mockito.verify(service.ud).writeToFile();
		
		// A user is created with the given arguments
		// That user is of type Player
		// That user has a starting Currency of 1000
		User u = captor.getValue();
		assertEquals(0, u.getBalance(), "Asserting starting balance is 0");
		assertEquals("Customer", u.getType(), "Asserting it is a Customer");
		assertEquals(username, u.getUsername(), "Asserting username is correct");
		assertEquals(password, u.getPassword(), "Asserting email is correct");
	}
}
	
	
	
	
