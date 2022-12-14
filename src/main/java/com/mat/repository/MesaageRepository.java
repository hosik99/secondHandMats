package com.mat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mat.model.Message;

public interface MesaageRepository extends JpaRepository<Message, Long>{

	List<Message> findAllByReceiver(String receiver);

	//안읽은 메세지 갯수
	@Query(value="SELECT COUNT(m.num) FROM Message m WHERE m.readed = 1 AND m.receiver = ?1", nativeQuery = false)
	Long unReadMsgCnt(String receiver);

}
