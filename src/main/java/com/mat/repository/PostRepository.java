package com.mat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mat.model.Post;


public interface PostRepository extends JpaRepository<Post,Long>{

	@Query(value="select DISTINCT p from Post p join  p.postImages",nativeQuery = false)
	Page<Post> findAllUsedFetch(Pageable pageable);

	Page<Post> findByWriter(String writer,Pageable pageable);

//	@Query(value="SELECT * FROM POSTS p INNER JOIN \r\n"
//			+ "(SELECT * FROM POST_IMAGES \r\n"
//			+ "WHERE ROWID IN (SELECT MAX(ROWID) FROM POST_IMAGES i2 GROUP BY POST_NUM)) i \r\n"
//			+ "ON p.POST_NUM = i.POST_NUM" ,nativeQuery = true)
//	List<Post> findAllUsedFetch();
	
}