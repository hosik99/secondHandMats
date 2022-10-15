package com.mat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private final String LGIN_FORM_PAGE = "thymeleaf/login/loginForm";
	
	@GetMapping("/loginFrom")
	public String showLoginFrom(@RequestParam(value="error",required = false)String error,
			@RequestParam(value="username",required = false)String username,Model model) {
		if(error!=null) model.addAttribute("error","True");
		return LGIN_FORM_PAGE;
	}

	@GetMapping("/logout")
	public void logout() {}
	
}
