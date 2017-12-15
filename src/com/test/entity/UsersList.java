package com.test.entity;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class UsersList {

	private ArrayList<Users> users = new ArrayList<>();

	public ArrayList<Users> getAllUsers() {
		return users;
	}

	public boolean addUsers(Users users) {
		this.users.add(users);
		return true;
	}

}
