package com.mat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mat.model.Member;
import com.mat.service.SignService;


@Controller
@RequestMapping("/sign")
public class SignUpController {
	
	private final String SIGNUP_FORM = "thymeleaf/signUp/signUpForm";
	private final String SIGNUP_SUCCESS = "thymeleaf/signUp/etc/signUpSuccess";
	
	@Autowired
	private SignService svc;
	
	//회원가입 폼으로 이동
	@GetMapping("/signUp/form")
	public String signUpForm(Model model) {
		model.addAttribute("member",new Member());
		return SIGNUP_FORM;
	}
	
	//회원가입 
	@PostMapping("/signUp")
	public ResponseEntity<Boolean> signUpForm(Member member) {
		boolean saved = svc.addUser(member);
		return new ResponseEntity<Boolean>(saved, HttpStatus.OK); 
	}
		
	//(회원가입) 이메일 인증 코드 보내는 메소드
	@PostMapping("/send/email/pass/{memberEmail}")
	public ResponseEntity<Boolean> sendEmailPass(@PathVariable("memberEmail")String memberEmail) {
		boolean sended = svc.sendEmailPass(memberEmail);
		return new ResponseEntity<Boolean>(sended, HttpStatus.OK); 
	}
	
	//이메일 인증 코드 비교
	@PostMapping("/check/emailCode")
	public ResponseEntity<Boolean> checkVerEmailCode(String emailCode,HttpSession session) {
		Boolean check= false;
		String randomCode = (String)session.getAttribute("randomCode");
		if(randomCode!=null && randomCode.equals(emailCode)){
	        if(randomCode!=null) session.removeAttribute("randomCode");
	        check = true;
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.OK); 
	}
	
	//이미 존재하는 아이디인지 확인
	@PostMapping("/exists/memberId")
	@ResponseBody
	public Boolean existsByMemberId(@RequestParam("memberId")String memberId) {
		return svc.existsByMemberId(memberId);
	}
	
	//로그인 성공 시 보여질 페이지로 이동
	@GetMapping("/signUp/success")
	public String signUpSuccess() {
		return SIGNUP_SUCCESS;
	}
}
