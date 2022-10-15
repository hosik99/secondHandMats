package com.mat.etc;

import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailManager {
	
	@Autowired
	private JavaMailSender sender;
	
	//상대 이메일에 인증코드 보내기
	public boolean sendEmailPass(String memberEmail) throws MessagingException {
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		String randomCode = getRandomText();
		session.setAttribute("randomCode", randomCode);
		
		MimeMessage mimeMessage = sender.createMimeMessage();
		try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress(memberEmail);
	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);
	         mimeMessage.setSubject("한돗 이메일 인증");
	         mimeMessage.setContent("<div>한돗 이메일 인증 번호</div><h3>"+randomCode+"</h3><div>회원가입 이메일 인증번호에 입력해주세요</div>", "text/html;charset=utf-8");
	         sender.send(mimeMessage);
	      }catch (MessagingException e) {
	         return false;
	      }
	      return true;
	}
	
	//인증코드 생성 메소드
	private String getRandomText() {
		UUID randomUUID = UUID.randomUUID();	
		String randomCode = randomUUID.toString().replaceAll("-","");
		return randomCode;
	}
}
