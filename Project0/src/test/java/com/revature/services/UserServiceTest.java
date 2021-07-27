package com.revature.services;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.User;
//import com.revature.beans.UserType;
import com.revature.data.UserDAO;

class UserServiceTest {

	private static UserService service;
	private static User testUser;
	
	@BeforeAll // Specifies that this static method will be run before any tests
	public static void setUpClass() {
		testUser = new User();
		testUser.setUsername("Test");
		testUser.setPassword("test123");
	}
	
	@BeforeEach // Specifies a method that is to be run before each test
	public void setUpTests() {
		service = new UserService(); // create a new userService for every test to maximize isolation
		service.ud = Mockito.mock(UserDAO.class);
		service.register(testUser.getUsername(), testUser.getPassword(), 0, "Customer");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		service.delete(testUser.getUsername());
	}

	
	@Test
	public void testSuccessfulLogin() {
		User user = service.login(testUser);
		
		// authenticating test user by logging in
		assertEquals(user != null, true ,"Authenticating user");		
	}
	
	@Test
	public void testUnSuccessfulLogin() {
		testUser.setPassword("changePassword");
		User user = service.login(testUser);
		
		// authenticating test user by logging in
		assertEquals(user != null, false ,"Authenticating user");		
	}
	
//	@Test
//	public void testRegister() {
//		String username = "test";
//		String password = "123";
//		service.register(username, password, 0, "Customer");
//		
		// An object that allows us to receive parameters from methods called on a Mockito mock object
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
//		assertEquals(1000l, u.getCurrency(), "Asserting starting currency is 1000");
//		assertEquals(UserType.PLAYER, u.getType(), "Asserting it is a Player");
//		assertEquals(username, u.getUsername(), "Asserting username is correct");
//		assertEquals(email, u.getEmail(), "Asserting email is correct");
//		assertEquals(date, u.getBirthday(), "Asserting birthday is correct");
//	}
}
