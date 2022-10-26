package com.mat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mat.model.Post;
import com.mat.security.SecurityUser;
import com.mat.service.PostService;


@Controller
@RequestMapping("/member/post")
public class PostController {
	
	private final String POST_FORM_PAGE = "thymeleaf/post/postUploadForm";
	private final String SAVE_SUCCESS_PAGE = "thymeleaf/post/etc/savedSuccess";
	private final String SHOW_POSTS_PAGE = "thymeleaf/post/showPosts";
	private final String SHOW_POST_DETAIL_PAGE = "thymeleaf/post/showDetailPost";

	
	
	@Autowired
	private PostService svc;
	
	//게시물 업로드 폼 이동
	@GetMapping("/postForm")
	public String postForm(Model model) {
		model.addAttribute("post",new Post());
		return POST_FORM_PAGE;
	}
	
	//게시물 저장
	@PostMapping("/savePostInfo")
	public String sendPostInfo(@Valid Post post,BindingResult result,Model model,
			@RequestParam("files")MultipartFile[] mfiles,@AuthenticationPrincipal SecurityUser principal) throws Exception {
		
		//파일 형식 확인
		boolean checkFileType = checkFileType(mfiles);
		if(result.hasErrors() || !checkFileType) {
			model.addAttribute("checkFilType",checkFileType);
			return POST_FORM_PAGE;
		}
		post.setWriter(principal.getUsername());
		Long saved = svc.savePostInfo(post,mfiles);
		
		if(saved!=null) return SAVE_SUCCESS_PAGE;
		throw new Exception("Post JPA 저장 에러발생");	//500에러 발생
	}
	
	//모든 게시물 가져오기
	@GetMapping("/showPosts")
	public String showPosts(Model model,@PageableDefault(size=6,sort="num",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Post> posts = svc.showPosts(pageable);
		List<Integer> pageList = svc.pageList(pageable);
		model.addAttribute("posts",posts);
		model.addAttribute("pageableHref","/member/post/showPosts?page=");
		model.addAttribute("pageList",pageList);
		model.addAttribute("LastIndexOfpage",pageList.size()-1);
		return SHOW_POSTS_PAGE;
	}
	
	//게시물 상세보기
	@GetMapping("/showPostDetail/{postNum}")
	public String showPostDetail(Model model,@PathVariable("postNum")Long postnum) throws Exception {
		Post post = svc.findById(postnum);
		if(post!=null) {
			model.addAttribute("post",post);
		}else {
			throw new Exception("Post 존재하지 않거나 삭제된 게시물 검색");
		}
		return SHOW_POST_DETAIL_PAGE;
	}
	
	private List<String> imgTypeList = new ArrayList<>(Arrays.asList("jpg","png","jfif"));
	//파일 형식 확인
	private boolean checkFileType(MultipartFile[] mfiles) {
		for(MultipartFile file : mfiles) {
			String orignName = file.getOriginalFilename();
			String fileType =null;
			if(!file.isEmpty()) fileType = orignName.substring(orignName.lastIndexOf(".")+1);
			if( fileType==null || !imgTypeList.contains(fileType)) return false;
		}
		return true;
	}
	
}
