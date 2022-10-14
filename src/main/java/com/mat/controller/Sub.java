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
@RequestMapping("/sub")
public class Sub {

	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private PostService postSVC;
	
	@GetMapping("/mem1")
	public void mem1() {
		Member member = new Member();
		member.setMemberId("mem1");
		member.setMemberEmail("mem1@mem.com");
		member.setName("mem1");
		member.setEnabled(false);
		member.setPassword(encoder.encode("mem1"));
		member.setRole(Role.ROLE_MEMBER);
		repository.save(member);
	}
	@GetMapping("/mem2")
	public void mem2() {
		Member member = new Member();
		member.setMemberId("mem2");
		member.setMemberEmail("mem2@mem.com");
		member.setName("mem2");
		member.setEnabled(false);
		member.setPassword(encoder.encode("mem2"));
		member.setRole(Role.ROLE_MEMBER);
		repository.save(member);
	}
	@GetMapping("/mem3")
	public void mem3() {
		Member member = new Member();
		member.setMemberId("mem3");
		member.setMemberEmail("mem3@mem.com");
		member.setName("mem3");
		member.setEnabled(false);
		member.setPassword(encoder.encode("mem3"));
		member.setRole(Role.ROLE_MEMBER);
		repository.save(member);
	}
	
	@GetMapping("/admin")
	public void insertPosts() {
		Member member = new Member();
		member.setMemberId("admin");
		member.setMemberEmail("admin@admin.com");
		member.setName("admin");
		member.setEnabled(false);
		member.setPassword(encoder.encode("admin"));
		member.setRole(Role.ROLE_ADMIN);
		repository.save(member);
	}
}
