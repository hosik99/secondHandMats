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

	// 에러 페이지 정의
	private final String ERROR_404_PAGE_PATH = "thymeleaf/errPage/noPageError";;
	private final String DEFAULT_ERROR_PAGE_PATH = "thymeleaf/errPage/defaultError";

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request, Model model) {

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			log.info("httpStatus : " + statusCode);

			// 404 error
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
