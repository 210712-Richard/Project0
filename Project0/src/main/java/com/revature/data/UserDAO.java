package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import com.revature.services.*;
import com.revature.beans.*;


public class UserDAO {
	private static String filename = "users.dat";
	private static List<User> users;
	


	static {
		DataSerializer<User> ds = new DataSerializer<User>();
		users = ds.readObjFromFile(filename);
		
		if(users == null) {
			users = new ArrayList<User>();
			users.add(new User("Brian", "password1", 100, "Customer"));
			users.add(new User("John", "password2", 50, "Customer"));
			users.add(new User("Alex", "password3", 20, "Customer"));
			User u = new User("Chris", "admin", 500, "Admin");
			users.add(u);
			ds.writeObjectsToFile(users, filename);
		}
	}

	
	public void addUser(User u) {
		users.add(u);
	}
	
	public List<User> getUsers(){
		return users;
	}
	
	public User getUser(String username) {
		for(User user : users) {
			if(user.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return user;
			}
		}
		return null;
	}
	
	public String getPassword(String username, String password) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user.getPassword();
			}
		}
		return null;
	}
	
	public void updateUser(User u) {
		for(User user : users) {
			if(user.getUsername().equalsIgnoreCase(u.getUsername())) {
				user = u;
				writeToFile();
			}
		}
	}
	
	public void writeToFile() {
		new DataSerializer<User>().writeObjectsToFile(users, filename);
	}
}
