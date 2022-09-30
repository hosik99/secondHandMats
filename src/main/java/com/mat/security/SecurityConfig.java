package com.mat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration   //설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@EnableWebSecurity
public class SecurityConfig{
	
	private String[] passPage = {"/","/member/post/showPosts","/member/post/showPostDetail/**"};
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Bean
	//protected void configure(HttpSecurity security) throws Exception {
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.userDetailsService(userDetailsService);

		http.authorizeRequests().antMatchers(passPage).permitAll();	//모든 이용자
		http.authorizeRequests().antMatchers("/member/**").authenticated(); //인증 통과 사용자만
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");	//인증 통과, ADMIN 권한만

		http.csrf().disable();
		http.formLogin().loginPage("/login/loginFrom").defaultSuccessUrl("/index/indexFrom", true);
		http.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/index/indexFrom");
		http.exceptionHandling().accessDeniedPage("/");	//액세스 거부 페이지
	
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
