package com.mat.service;


import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	//인증메일 보내기
	public boolean sendEmailPass(String memberEmail) {
		try {
			emailMgr.sendEmailPass(memberEmail);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	//유저 추가
	public boolean addUser(Member member) {
		String originPw = member.getPassword();
		member.setPassword(encoder.encode(originPw));
		Member savedUser = memberRPS.save(member);
		boolean saved = savedUser!=null ? true : false;
		return saved;
	}
	
	//유저의 아이디가 이미 존재하는지 확인
	public boolean existsByMemberId(String username) {
		return memberRPS.existsByMemberId(username);
	}
}
