package com.mat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mat.model.PostImages;



public interface PostImgRepository extends JpaRepository<PostImages,Long>{

	void deleteByFpath(String imgName);

}
