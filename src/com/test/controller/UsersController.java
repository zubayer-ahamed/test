package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.entity.IdGenerator;
import com.test.entity.Users;
import com.test.entity.UsersList;
import com.test.helper.UsersHelper;

@Controller
public class UsersController {

	@Autowired
	private UsersList usersList;
	@Autowired
	private IdGenerator idGenerator;

//	@InitBinder
//	public void myInitBinder(WebDataBinder binder) {
//		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//		binder.registerCustomEditor(Date.class, "dateOfBirth", new CustomDateEditor(format, false));
//	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loadFormPage(Model model) {
		Users users = new Users();
		model.addAttribute("pageTitle", "Home");
		model.addAttribute("users", users);
		model.addAttribute("usersList", usersList.getAllUsers());
		model.addAttribute("allHobbies", new UsersHelper().getUsersHobbies());
		model.addAttribute("allLanguages", new UsersHelper().getUsersLanguages());
		model.addAttribute("allDistricts", new UsersHelper().getUsersDistricts());
		return "index";
	}

	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
	public String saveUserInfo(Model model, @Valid @ModelAttribute("users") Users users, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<>();
			map.put("usersList", usersList.getAllUsers());
			map.put("allHobbies", new UsersHelper().getUsersHobbies());
			map.put("allLanguages", new UsersHelper().getUsersLanguages());
			map.put("allDistricts", new UsersHelper().getUsersDistricts());
			model.addAllAttributes(map);
			return "index";
		}
		int generatedId = 1;
		if (idGenerator.getId() != 0) {
			generatedId = idGenerator.getId() + 1;
		}
		users.setUid(generatedId);
		idGenerator.setId(generatedId);
		usersList.getAllUsers().add(users);
		return "redirect:/";
	}

	@RequestMapping(value = "/editUser/{uid}", method = RequestMethod.GET)
	public String editUser(Model model, @PathVariable("uid") int uid) {
		ArrayList<Users> list = usersList.getAllUsers();
		for (Users u : list) {
			if (u.getUid() == uid) {
				model.addAttribute("users", u);
				model.addAttribute("usersList", usersList.getAllUsers());
				model.addAttribute("allHobbies", new UsersHelper().getUsersHobbies());
				model.addAttribute("allLanguages", new UsersHelper().getUsersLanguages());
				model.addAttribute("allDistricts", new UsersHelper().getUsersDistricts());
			}
		}

		return "editUser";
	}

	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(Model model, @Valid @ModelAttribute("users") Users users, BindingResult result) {
		ArrayList<Users> tempList = new ArrayList<>();
		for (Users u : usersList.getAllUsers()) {
			if (u.getUid() != users.getUid()) {
				tempList.add(u);
			} else {
				tempList.add(users);
			}
		}
		usersList.getAllUsers().clear();
		for (Users u : tempList) {
			usersList.getAllUsers().add(u);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/deleteUser/{uid}", method = RequestMethod.GET)
	public String deleteUsersInfo(Model model, @PathVariable("uid") int uid) {
		ArrayList<Users> tempList = new ArrayList<>();
		for (Users u : usersList.getAllUsers()) {
			if (u.getUid() != uid) {
				tempList.add(u);
			}
		}
		usersList.getAllUsers().clear();
		for (Users u : tempList) {
			usersList.getAllUsers().add(u);
		}
		return "redirect:/";
	}
}
