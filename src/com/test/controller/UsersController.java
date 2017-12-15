package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.entity.Users;
import com.test.entity.UsersList;
import com.test.helper.UsersHelper;

@Controller
public class UsersController {

	@Autowired
	private UsersList usersList;

	@InitBinder
	public void myInitBinder(WebDataBinder binder) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, "dateOfBirth", new CustomDateEditor(format, false));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loadFormPage(Model model) {
		Users users = new Users();
		users.setUid(usersList.getAllUsers().size() + 1);
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
		usersList.getAllUsers().add(users);
		return "redirect:/";
	}
}
