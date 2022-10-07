package com.mat.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;

import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect	
@Configuration 
public class MyPageAop {
	
	@Autowired
	private MesaageService mesaageService;
	
//	@AfterReturning("execution(* com.mat.controller.MyPageController.*(..))") 
//    public void before(Model model,@AuthenticationPrincipal SecurityUser principal){
//		Long unReadMsgCnt = mesaageService.unReadMsgCnt(principal.getUsername());
//    	model.addAttribute("unReadMsgCnt",unReadMsgCnt);
//    }
	
	@AfterReturning("execution(* com.mat.controller.MyPageController.*(..))") 
    public void before(){
    }
	
}
