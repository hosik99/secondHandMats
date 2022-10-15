package com.mat.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString(exclude = "post")
@Table(name="PostImages")
public class PostImages {
	
	@Id @GeneratedValue
	@Column(name="post_image_num")
	private Long num;
	
	//저장된 경로
	private String fpath;	
	
	private Date udate = Date.valueOf(LocalDate.now());
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_num")
	private Post post;
	
	public void setPost(Post post) {
		this.post = post;
		if(!post.getPostImages().contains(this)) post.getPostImages().add(this);
	}
}
