package com.mat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecrityController {

	@GetMapping("/admin/adminPage")
	public void admin() {}
}
