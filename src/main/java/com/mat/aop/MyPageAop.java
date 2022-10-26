package com.mat.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect	//공통 기능과 PointCut을 설정한다
@Configuration //설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션 
public class MyPageAop {
	
	@Autowired
	private MesaageService mesaageService;
	
	//마이페이지 - 아직 안읽은 메세지의 수를 표시하기 위한 unReadMsgCnt() 메소드
	@AfterReturning("execution(* com.mat.controller.MyPageController.*(..))") 
    public void unReadMsgCnt(){
		addModelUnreadMsg();
    }
	
	//메세지 - 아직 안읽은 메세지의 수를 표시하기 위한 unReadMsgCnt() 메소드
	@AfterReturning("execution(* com.mat.controller.MessageController.*(..))") 
    public void unReadMsgCnt2(){
		addModelUnreadMsg();
	}
	
	//model에 안읽은 메세지 수 추가
	private void addModelUnreadMsg() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		SecurityUser principal = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Long unReadMsgCnt = mesaageService.unReadMsgCnt(principal.getUsername());
		request.setAttribute("messageCnt", unReadMsgCnt);
	}
}
