package com.mat.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ExceptionHandlingController implements ErrorController {
	//ErrorController -> 404,500등의 오류코드를 관리하기 위해서 구현
	
	// 에러 페이지 정의
	private final String ERROR_404_PAGE_PATH = "thymeleaf/errPage/noPageError";;
	private final String DEFAULT_ERROR_PAGE_PATH = "thymeleaf/errPage/defaultError";

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request, Model model) {

		//RequestDispatcher -> 특정 자원에 처리를 요청하고 처리 결과를 얻어오는 기능을 수행하는 클래스
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		//status-> 404 or 500같은 에러 코드가 있음
		//httpStatus -> 404 NOT_FOUND
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			log.info("httpStatus : " + statusCode);

			// 404 error
			//HttpStatus.NOT_FOUND.value() -> 404
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return ERROR_404_PAGE_PATH;
			}
			// 500 error
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return DEFAULT_ERROR_PAGE_PATH;
			}
		}
		return DEFAULT_ERROR_PAGE_PATH;
	}

	public String getErrorPath() {
		return "/error";
	}

}
