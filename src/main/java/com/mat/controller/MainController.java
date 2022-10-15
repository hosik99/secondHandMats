package com.mat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mat.model.Post;
import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;
import com.mat.service.PostService;

@Controller
@RequestMapping("/index")
public class MainController {
	
	private final String INDEX_PAGE = "thymeleaf/index";
	
	@Autowired
	private PostService postSVC;
	@Autowired
	private MesaageService messageSVC;
	
	@GetMapping("/indexForm")
	public String main(Model model,@PageableDefault(size=6,sort="num",direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal() SecurityUser principal) {
		
		//하단에 게시물 표시
		Page<Post> posts = postSVC.showPosts(pageable);
		model.addAttribute("posts",posts);
		
		//로그인 시 유저가 안읽은 메세지 수 표시
		if( principal != null) {
			Long messageCnt = messageSVC.unReadMsgCnt(principal.getUsername());
			model.addAttribute("messageCnt",messageCnt);
		}
		return INDEX_PAGE;
	}
}
