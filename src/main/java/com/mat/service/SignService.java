package com.mat.service;


import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mat.enums.Role;
import com.mat.etc.EmailManager;
import com.mat.model.Member;
import com.mat.repository.MemberRepository;


@Service
public class SignService {
	
	@Autowired
	private EmailManager emailMgr;
	@Autowired
	private MemberRepository memberRPS;
	@Autowired
	private PasswordEncoder encoder;
	
	public boolean sendEmailPass(String memberEmail) {
		try {
			emailMgr.sendEmailPass(memberEmail);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUser(Member member) {
		String originPw = member.getPassword();
		member.setPassword(encoder.encode(originPw));
		Member savedUser = memberRPS.save(member);
		boolean saved = savedUser!=null ? true : false;
		return saved;
	}
	
	public boolean existsByMemberId(String username) {
		return memberRPS.existsByMemberId(username);
	}
}
