package com.mat.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString(exclude = "postImages")
@Table(name="posts")
public class Post {
	
	@Id @GeneratedValue
	@Column(name="post_num")
	private Long num;
	
	@NotEmpty
	@Column(length = 30)
	private String title;
	
	@NotEmpty
	@Column(length = 30)
	private String location;
	
	@NotEmpty
	@Column(length = 500)
	private String contents;
	
	private String writer;
	
	private Date wdate = Date.valueOf(LocalDate.now());
	
	@OneToMany(mappedBy = "post",fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PostImages> postImages = new ArrayList<PostImages>();
	
	public void addMember(PostImages postImage) {
		this.postImages.add(postImage);
		if(postImage.getPost()!=this) postImage.setPost(this);
	}
}
