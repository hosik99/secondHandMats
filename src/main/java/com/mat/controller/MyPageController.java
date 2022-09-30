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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mat.etc.ViewHref;
import com.mat.model.Post;
import com.mat.security.SecurityUser;
import com.mat.service.PostService;

@Controller
@RequestMapping("/member/mypage")
public class MyPageController {
	
	@Autowired
	private ViewHref viewHref;
	@Autowired
	private PostService postSVC;
	
	//개인 페이지 이동
	@GetMapping("/main")
	public String myPageMain() {
		return viewHref.getMyPageMain();
	}
	
	//개인페이지 -> 게시물 관리 -> 내가 올린 게시물 클릭
	@GetMapping("/myPosts")
	public String myPosts(Model model,@AuthenticationPrincipal SecurityUser principal,
			@PageableDefault(size=6,sort="num",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Post> myPosts = postSVC.getMyPosts(principal.getUsername(),pageable);
		model.addAttribute("myPosts",myPosts);
		return viewHref.getMyPostsPage();
	}
	
	//개인페이지 -> 게시물 관리 -> 내가 올린 게시물 -> 게시물 삭제
	@PostMapping("/deletePost")
	@ResponseBody
	public String deletePost(@RequestParam("postNum")Long postNum) {
		Boolean deleted = postSVC.deletePost(postNum);
		return deleted+"";
	}
	
	//개인페이지 -> 게시물 관리 -> 내가 올린 게시물 -> 게시물 수정 폼
	@GetMapping("/updatePost/from/{postNum}")
	public String updatePostFrom(@PathVariable("postNum")Long postNum,Model model) {
		Post post = postSVC.findById(postNum);
		model.addAttribute("post",post);
		return viewHref.getUpdatePostFrom();
	}
	
	//개인페이지 -> 게시물 관리 -> 내가 올린 게시물 -> 게시물 수정 폼 -> 이미지 삭제
	@PostMapping("/deleteImg")
	@ResponseBody
	public String deleteImg(@RequestParam("imgNum")Long imgNum) {
		Boolean deleted = postSVC.deleteImgById(imgNum);
		return deleted+"";
	}
}
