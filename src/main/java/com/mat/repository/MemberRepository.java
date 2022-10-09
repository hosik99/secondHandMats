package com.mat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.mat.model.Member;


public interface MemberRepository extends JpaRepository<Member, String> {

	Boolean existsByMemberId(String username);
}
