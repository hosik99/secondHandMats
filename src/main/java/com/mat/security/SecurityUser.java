package com.mat.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.mat.model.Member;


public class SecurityUser extends User {	//UserDetail객체 사용하기 위해
	private static final long serialVersionUID = 1L;
	private Member member;

	public SecurityUser(Member member) {		//암호화 시 {noop}제거
		super(member.getMemberId(),member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}

	public Member getMember() {
		return member;
	}
}
