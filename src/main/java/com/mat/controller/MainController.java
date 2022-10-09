package com.mat.controller;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

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

import com.mat.etc.ViewHref;
import com.mat.model.Post;
import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;
import com.mat.service.PostService;

@Controller
@RequestMapping("/index")
public class MainController {
	
	@Autowired
	private ViewHref viewHref;
	@Autowired
	private PostService postSVC;
	@Autowired
	private MesaageService messageSVC;
	
	@GetMapping("/indexForm")
	public String main(Model model,@PageableDefault(size=6,sort="num",direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal() SecurityUser principal) {
		Page<Post> posts = postSVC.showPosts(pageable);
		model.addAttribute("posts",posts);
		if( principal != null) {
			Long messageCnt = messageSVC.unReadMsgCnt(principal.getUsername());
			model.addAttribute("messageCnt",messageCnt);
		}
		return viewHref.getIndexPage();
	}
}
