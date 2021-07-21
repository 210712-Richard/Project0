package com.revature.services;

import com.revature.beans.User;
import com.revature.data.UserDAO;

public class UserService {
	public UserDAO ud = new UserDAO();
	
	
	public User login(String username) {
		User u = ud.getUser(username);
		return u;
	}
	
	
	public void register(String username, String password, double balance, String type) {
		User u = new User(username, password, balance, type);
		ud.addUser(u);
		ud.writeToFile();
	}
	
	public void updateUser(User u) {
		ud.updateUser(u);
	}
}
