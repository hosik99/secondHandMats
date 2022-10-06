package com.mat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.mat.enums.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="")	//exclude-> 특정 결과를 toString에서 제외
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
