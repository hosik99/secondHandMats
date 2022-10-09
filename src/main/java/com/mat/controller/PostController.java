package com.mat.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mat.etc.ViewHref;
import com.mat.model.Post;
import com.mat.model.PostImages;
import com.mat.security.SecurityUser;
import com.mat.service.PostService;


@Controller
@RequestMapping("/member/post")
public class PostController {
	
	private List<String> imgTypeList = new ArrayList<>(Arrays.asList("jpg","png","jfif"));
	
	@Autowired
	private ViewHref viewHref;
	@Autowired
	private PostService svc;
	
	//게시물 업로드 폼 이동
	@GetMapping("/postForm")
	public String postForm(Model model) {
		model.addAttribute("post",new Post());
		return viewHref.getPostFromPage();
	}
	
	//게시물 저장
	@PostMapping("/savePostInfo")
	public String sendPostInfo(@Valid Post post,BindingResult result,Model model,
			@RequestParam("files")MultipartFile[] mfiles,@AuthenticationPrincipal SecurityUser principal) {
		boolean checkFileType = checkFileType(mfiles);
		if(result.hasErrors() || !checkFileType) {
			model.addAttribute("checkFilType",checkFileType);
			return viewHref.getPostFromPage();
		}
		post.setWriter(principal.getUsername());
		Long saved = svc.savePostInfo(post,mfiles);
		
		if(saved!=null) return viewHref.getSaveSuccessPage();
		return viewHref.getErrorPage();
	}
	
	@GetMapping("/showPosts")
	public String showPosts(Model model,@PageableDefault(size=6,sort="num",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Post> posts = svc.showPosts(pageable);
		List<Integer> pageList = svc.pageList(pageable);
		model.addAttribute("posts",posts);
		model.addAttribute("pageableHref","/member/post/showPosts?page=");
		model.addAttribute("pageList",pageList);
		model.addAttribute("LastIndexOfpage",pageList.size()-1);
		return viewHref.getShowPostsPage();
	}
	
	@GetMapping("/showPostDetail/{postNum}")
	public String showPostDetail(Model model,@PathVariable("postNum")Long postnum) {
		Post post = svc.findById(postnum);
		if(post!=null) {
			model.addAttribute("post",post);
		}else {
			viewHref.getNoPageError();
		}
		return viewHref.getShowPostDetailPage();
	}
	
	
	//method
	private boolean checkFileType(MultipartFile[] mfiles) {
		for(MultipartFile file : mfiles) {
			String orignName = file.getOriginalFilename();
			String fileType =null;
			if(!file.isEmpty()) fileType = orignName.substring(orignName.length()-3);
			if( fileType==null || !imgTypeList.contains(fileType)) return false;
		}
		return true;
	}
	
}
