package com.mat.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Entity
@Table(name="message")
public class Message {
	
	@Id @GeneratedValue
	@Column(name="MESSAGE_NUM")
	private Long num;
	
	private String sender;
	
	@NotEmpty
	private String receiver;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String contents;
	
	private Date writeDate = Date.valueOf(LocalDate.now());
	
	private int readed = 1;
}
