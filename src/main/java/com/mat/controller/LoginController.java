package com.mat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mat.etc.ViewHref;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private ViewHref viewHref;
	
	@GetMapping("/loginFrom")
	public String showLoginFrom() {
		return viewHref.getLoginFromPage();
	}

	@GetMapping("/logout")
	public void logout() {}
	
}
