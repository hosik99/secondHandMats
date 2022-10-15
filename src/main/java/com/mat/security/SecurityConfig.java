package com.mat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration   
@EnableWebSecurity
public class SecurityConfig{
	
	private String[] passPage = {"/","/member/post/showPosts","/member/post/showPostDetail/**"};
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.userDetailsService(userDetailsService);

		return http.authorizeRequests().antMatchers(passPage).permitAll()
			.antMatchers("/member/**").authenticated()
			.antMatchers("/admin/**").hasRole("ADMIN")	
			.and()
			
			.csrf().disable()
			.formLogin().loginPage("/login/loginFrom").defaultSuccessUrl("/index/indexForm", true)
			.failureUrl("/login/loginFrom?error=True")
			.and()
			
			.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/index/indexForm")
			.and()
			
			.exceptionHandling().accessDeniedPage("/")
			.and().build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
