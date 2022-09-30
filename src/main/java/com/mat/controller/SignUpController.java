package com.mat.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mat.etc.ViewHref;
import com.mat.model.Member;
import com.mat.service.SignService;


@Controller
@RequestMapping("/sign")
public class SignUpController {
	
//	private String signUpForm = "thymeleaf/signUp/signUpForm";
//	private String signUpSuccess = "thymeleaf/signUp/etc/signUpSuccess";
	
	@Autowired
	private ViewHref viewHref;
	@Autowired
	private SignService svc;
	
	//회원가입 폼으로 이동
	@GetMapping("/signUp/form")
	public String signUpForm(Model model) {
		model.addAttribute("member",new Member());
		return viewHref.getSignUpForm();
	}
	
	//회원가입 
	@PostMapping("/signUp/form")
	public String signUpForm(@Valid Member member,BindingResult result,Model model,HttpSession session,@RequestParam("verEmailCode")String verEmailCode) {
		String randomCode = (String)session.getAttribute("randomCode");
		if(result.hasErrors()) return viewHref.getSignUpForm();
		if(randomCode!=null && randomCode.equals(verEmailCode)){
			boolean saved = svc.addUser(member);
			Object obj = session.getAttribute("randomCode");
	        if(obj!=null) session.removeAttribute("randomCode");
	        return viewHref.getSignUpSuccess();
		}
		return viewHref.getSignUpForm();
	}
		
	//(회원가입) 이메일 인증 코드 보내는 메소드
	@PostMapping("/send/email/pass/{memberEmail}")
	public ResponseEntity<Boolean> sendEmailPass(@PathVariable("memberEmail")String memberEmail) {
		boolean sended = svc.sendEmailPass(memberEmail);
		return new ResponseEntity<Boolean>(sended, HttpStatus.OK); 
	}
}
