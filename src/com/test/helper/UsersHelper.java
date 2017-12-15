package com.test.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.entity.UsersList;

@Component
public class UsersHelper {

	private ArrayList<String> hobbies;
	private ArrayList<String> languages;
	private ArrayList<String> districts;
	private int generatedId;

	@Autowired
	private UsersList usersList;

	public int getGeneratedUserId() {
		return usersList.getAllUsers().size() + 1;
	}

	public ArrayList<String> getUsersDistricts() {
		districts = new ArrayList<>();
		districts.add("dhaka");
		districts.add("comilla");
		districts.add("rajshahi");
		districts.add("sylhet");
		districts.add("chittagong");
		return districts;
	}

	public ArrayList<String> getUsersHobbies() {
		hobbies = new ArrayList<>();
		hobbies.add("cricket");
		hobbies.add("football");
		hobbies.add("racing");
		return hobbies;
	}

	public ArrayList<String> getUsersLanguages() {
		languages = new ArrayList<>();
		languages.add("bangla");
		languages.add("hindi");
		languages.add("english");
		return languages;
	}

}
