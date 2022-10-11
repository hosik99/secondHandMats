package com.mat.etc;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ViewHref {
	
	//index 페이지 링크
	private String indexPage = "thymeleaf/index";
	
	//로그인 폼 페이지 링크
	private String loginFromPage = "thymeleaf/login/loginForm";
	
	//회원가입 관련
	private String signUpForm = "thymeleaf/signUp/signUpForm";
	private String signUpSuccess = "thymeleaf/signUp/etc/signUpSuccess";
	
	
	//메세지 관련 링크
	private String msgSendFrom = "thymeleaf/message/messageSendFrom";
	private String showAllMsg = "thymeleaf/message/showAllMsg";
	private String showMsgDetail = "thymeleaf/message/showMessageDetail";
	
	//마이페이지 관련 링크
	private String myPageMain = "thymeleaf/myPage/myPageMain";
	private String myPostsPage = "thymeleaf/myPage/myPosts";
	private String updatePostFrom = "thymeleaf/myPage/updateFrom";

	//게시판 관련
	private final String POST_FORM_PAGE = "thymeleaf/post/postUploadForm";
	private final String SAVE_SUCCESS_PAGE = "thymeleaf/post/etc/savedSuccess";
	private final String SHOW_POSTS_PAGE = "thymeleaf/post/showPosts";
	private final String SHOW_POST_DETAIL_PAGE = "thymeleaf/post/showDetailPost";

	//에러 페이지
	private String errorPage = "thymeleaf/errPage/defaultError";
	private String noPageError = "thymeleaf/errPage/noPageError";

	
}
