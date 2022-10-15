package com.mat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mat.model.Post;


public interface PostRepository extends JpaRepository<Post,Long>{

	@Query(value="select DISTINCT p from Post p join  p.postImages",nativeQuery = false)
	Page<Post> findAllUsedFetch(Pageable pageable);

	Page<Post> findByWriter(String writer,Pageable pageable);
}