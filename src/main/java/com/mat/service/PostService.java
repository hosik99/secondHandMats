package com.mat.service;

import java.io.File;
import java.time.LocalDate;
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
	
	public Boolean savePostInfo(Post post,MultipartFile[] mfiles) {
		Post post_1 = postRepository.save(post);
		List<PostImages> imageList = addFileToList(mfiles,post);
		if(imageList!=null) imgRepository.saveAll(imageList);
		System.out.println("awdwad: "+post_1.getNum() != null);
		return post_1.getNum() != null;
	}
	

	public Page<Post> showPosts(Pageable pageable) {
		Page<Post> posts =  postRepository.findAllUsedFetch(pageable);
		for(Post post : posts) {
			List<PostImages> list = post.getPostImages();
		}
		return posts;
	}
	
	public Post findById(Long postnum) {
		Optional<Post> post= postRepository.findById(postnum);
		Post result = post.isPresent() ?  post.get() :  null;
		return result;
	}
	

	public Page<Post> getMyPosts(String writer,Pageable pageable) {
		Page<Post> myPosts = postRepository.findByWriter(writer,pageable);
		return myPosts;
	}
	
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
	
	/* method */
	private List<PostImages> addFileToList(MultipartFile[] mfiles,Post post) {
		String savePath = getSavePath()+"\\postImages";
		
		List<PostImages> imageList = new ArrayList<>();
		
		try {
			for(int i=0;i<mfiles.length;i++) {
				
				String orignName = mfiles[i].getOriginalFilename();	//원래 이름
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
	
	public String getSavePath() {
		//파일 저장 경로
		String path = "src/main/resources/static";
		File file = new File(path);
		String savePath = file.getAbsolutePath();
				
		//파일 없으면 생성
		File dir = new File(savePath+"/postImages");
		if(dir.exists()==false) dir.mkdirs();
		return savePath;
	}

	public List<Integer> pageList(Pageable pageable) {
		Long dataCount = postRepository.count();
		int startNum = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int pageCnt = (int)Math.ceil(dataCount/size);	
		
		//view 하단에 보여줄 페이지 목록
		List<Integer> pageList = new ArrayList();
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
