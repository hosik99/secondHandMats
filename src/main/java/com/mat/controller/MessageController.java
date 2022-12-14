package com.mat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mat.model.Message;
import com.mat.security.SecurityUser;
import com.mat.service.MesaageService;

@Controller
@RequestMapping("/member/message")
public class MessageController {
	
	private final String MSG_SEND_FORM = "thymeleaf/message/messageSendFrom";
	private final String SHOW_ALL_MSG = "thymeleaf/message/showAllMsg";
	private final String SHOW_MSG_DETAIL = "thymeleaf/message/showMessageDetail";
	
	@Autowired
	private MesaageService messageSVC;
	
	//메세지 입력 폼으로 이동
	@GetMapping("/msgSendFrom")
	public String msgSendFrom(Model model,@AuthenticationPrincipal() SecurityUser principal) {
		model.addAttribute("message",new Message());
		return MSG_SEND_FORM;
	}
	
	//메세지 입력 폼에서 보내기 클릭 -> 메세지 저장
	@PostMapping("/saveMsg")
	@ResponseBody		
	public String saveMsg(@Valid Message message,BindingResult result,Model model,
			@AuthenticationPrincipal SecurityUser principal) {
		if(result.hasErrors()) return MSG_SEND_FORM;
		message.setSender(principal.getUsername());
		Boolean saved = messageSVC.saveMessage(message);
		return saved+"";
	}
	
	//메세지 목록 보기
	@GetMapping("/showAllMsg")
	public String showAllMsg(Model model,@AuthenticationPrincipal SecurityUser principal) {
		List<Message> messages = messageSVC.showAllMsg(principal.getUsername());
		model.addAttribute("messages",messages);
		return SHOW_ALL_MSG;
	}
		
	//메세지 상세보기
	@GetMapping("/showMsgDetail/{messagesNum}")
	public String showMsgDetail(@PathVariable Long messagesNum,Model model,
			@AuthenticationPrincipal SecurityUser principal) {
		Message message = messageSVC.showMsgDetail(messagesNum);
		model.addAttribute("message",message);
		return SHOW_MSG_DETAIL;
	}
	
	//메세지 삭제
	@PostMapping("/deleteMessage")
	@ResponseBody
	public String deleteMessage(@RequestParam(value="checkList[]")Long[] checkList) {
		Boolean deleted = messageSVC.deleteMessage(checkList);
		return deleted+"";
	}
}