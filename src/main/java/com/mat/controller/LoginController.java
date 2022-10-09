package com.mat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mat.etc.ViewHref;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private ViewHref viewHref;
	
	@GetMapping("/loginFrom")
	public String showLoginFrom(@RequestParam(value="error",required = false)String error,
			@RequestParam(value="username",required = false)String username,Model model) {
		if(error!=null) model.addAttribute("error","True");
		return viewHref.getLoginFromPage();
	}

	@GetMapping("/logout")
	public void logout() {}
	
}
