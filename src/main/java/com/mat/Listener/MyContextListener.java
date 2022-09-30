package com.mat.Listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyContextListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent sce) throws RuntimeException{
		ServletContext context = sce.getServletContext();
		log.info("<--웹 어플리케이션 시작-->");
		log.info("서버 정보: "+context.getServerInfo());
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		log.info("<--웹 어플리케이션 종료-->");
		log.info("서버 정보: "+context.getServerInfo());
	}
	
}
