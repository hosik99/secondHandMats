package com.mat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mat.enums.Role;
import com.mat.model.Member;
import com.mat.repository.MemberRepository;
import com.mat.service.PostService;

@Controller
@RequestMapping("/a")
public class test {

	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private PostService postSVC;
	
	@GetMapping("/oo")
	public void oo() {
		Member member = new Member();
		member.setMemberId("123");
		member.setMemberEmail("Awd@awda.com");
		member.setName("aa");
		member.setEnabled(false);
		member.setPassword(encoder.encode("123"));
		member.setRole(Role.ROLE_ADMIN);
		repository.save(member);
	}
	
	@GetMapping("/insertPosts")
	public void insertPosts() {
		Member member = new Member();
		member.setMemberId("123");
		member.setName("aa");
		member.setEnabled(false);
		member.setPassword(encoder.encode("123"));
		member.setRole(Role.ROLE_ADMIN);
		repository.save(member);
	}
}
