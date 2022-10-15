package com.mat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mat.model.Post;
import com.mat.model.PostImages;
import com.mat.repository.PostImgRepository;
import com.mat.repository.PostRepository;



@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostImgRepository imgRepository;
	
	//게시물 저장
	public Long savePostInfo(Post post,MultipartFile[] mfiles) {
		Post post_1 = postRepository.save(post);
		List<PostImages> imageList = addFileToList(mfiles,post);
		if(imageList!=null) imgRepository.saveAll(imageList);
		return post_1.getNum();
	}
	
	//모든 게시물 가져오기
	public Page<Post> showPosts(Pageable pageable) {
		Page<Post> posts =  postRepository.findAllUsedFetch(pageable);
		return posts;
	}
	
	//게시물 상세보기
	public Post findById(Long postnum) {
		Optional<Post> post= postRepository.findById(postnum);
		Post result = post.isPresent() ?  post.get() :  null;
		return result;
	}
	
	//내가 작성한 게시물 보가져오기
	public Page<Post> getMyPosts(String writer,Pageable pageable) {
		Page<Post> myPosts = postRepository.findByWriter(writer,pageable);
		return myPosts;
	}
	
	//게시물 삭제
	public boolean deletePost(Long postNum) {
		Boolean deleted;
		try {
			postRepository.deleteById(postNum);
			deleted = true;
		}catch(Exception e) {
			e.printStackTrace();
			deleted = false;
		}
		return deleted;
	}
	
	//게시물 이미지 삭제
	public boolean deleteImgById(Long imgNum) {
		Boolean deleted;
		try {
			imgRepository.deleteById(imgNum);
			deleted = true;
		}catch(Exception e) {
			e.printStackTrace();
			deleted = false;
		}
		return deleted;
	}
	
	//사진 파일 저장
	private List<PostImages> addFileToList(MultipartFile[] mfiles,Post post) {
		String savePath = getSavePath()+"\\postImages";
		
		List<PostImages> imageList = new ArrayList<>();
		
		try {
			for(int i=0;i<mfiles.length;i++) {
				
				String orignName = mfiles[i].getOriginalFilename();
				int lastIdx = orignName.lastIndexOf("."); 
				String orignName_1 = orignName.substring(0, lastIdx);
				String orignName_2 = orignName.substring(lastIdx);
				
				String fname_changed = orignName_1+System.nanoTime()+orignName_2;
				String filePath = savePath+"\\"+fname_changed;
				
				PostImages image = new PostImages();
				image.setFpath(fname_changed);
				image.setPost(post);
				System.out.println("filePath: "+filePath);
				mfiles[i].transferTo(new File(filePath));
				
				imageList.add(image);
			}
			return imageList;
		}catch(Exception e) {
			return null;
		}
	}
	
	//저장경로 가져오는 메소드
	public String getSavePath() {
		String path = "src/main/resources/static";
		File file = new File(path);
		String savePath = file.getAbsolutePath();
				
		File dir = new File(savePath+"/postImages");
		if(dir.exists()==false) dir.mkdirs();
		return savePath;
	}

	//view 하단에 보여줄 페이지 목록
	public List<Integer> pageList(Pageable pageable) {
		Long dataCount = postRepository.count();
		int startNum = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int pageCnt = (int)Math.ceil(dataCount/size);	
		
		List<Integer> pageList = new ArrayList<Integer>();
		int i= startNum-3;
		while(pageList.size()<5) {
			++i;
			if(i<0)continue;
			if(i>pageCnt) break;
			pageList.add(i);
		} 
		return pageList;
	}


	


	


}
