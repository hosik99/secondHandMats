package com.mat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mat.model.Message;
import com.mat.repository.MesaageRepository;

@Service
public class MesaageService {
	
	@Autowired
	private MesaageRepository messageRPS;

	//메세지 저장
	public Boolean saveMessage(Message message) {
		Message saved = messageRPS.save(message);
		return saved.getNum()!= null;
	}
	
	//유저에게 온 모든 메세지
	public List<Message> showAllMsg(String receiver) {
		List<Message> messages = messageRPS.findAllByReceiver(receiver);
		return messages;
	}

	//메세지 상세 보기
	public Message showMsgDetail(Long messagesNum) {
		Optional<Message> message = messageRPS.findById(messagesNum);
		Message result = message.isPresent() ?  message.get() :  null;
		return result;
	}

	//메세지 삭제
	@Transactional
	public Boolean deleteMessage(Long[] checkList) {
		try {
			for(int i=0;i<checkList.length;i++) {
				messageRPS.deleteById(checkList[i]);
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

	//안읽은 메세지 갯수
	public Long unReadMsgCnt(String member) {
		return messageRPS.unReadMsgCnt(member);
	}
}
