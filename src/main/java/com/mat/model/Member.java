package com.mat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.mat.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="MEMBER")
public class Member { 
	
	@Id 
	@Column(name="MEMBER_ID")
	@NotEmpty
	private String memberId;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String memberEmail;
	
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_MEMBER;
	
	private boolean enabled;
	
}
