package com.mat.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect	
@Configuration 
public class MyPageAop {
	
	@Autowired
	private MesaageService mesaageService;
	
	@AfterReturning("execution(* com.mat.controller.MyPageController.*(..))") 
    public void before(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		SecurityUser principal = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Long unReadMsgCnt = mesaageService.unReadMsgCnt(principal.getUsername());
		request.setAttribute("messageCnt", unReadMsgCnt);
    }
	
}
