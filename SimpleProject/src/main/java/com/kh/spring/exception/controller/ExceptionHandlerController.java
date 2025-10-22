package com.kh.spring.exception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.UserIdNotFoundException;
import com.kh.spring.member.model.dao.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
//	@ExceptionHandler(TooLargeValueException.class)
//	protected void largeValueError() {
//		log.info("길이가 길면 정말 얘가 출동함?");
//	}
//	@ExceptionHandler(InvalidArgumentsException.class)
//	protected void invlaildArgumentError() {
//		log.info("값이 없으면 정말 얘가 호출됨?");
//	}
	
	
	private ModelAndView createErrorResponse(RuntimeException e)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg",e.getMessage()).setViewName("include/error");
		log.info("발생에외 :{}", e);
		return mv;	
	}
	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView authenticationError(AuthenticationException e) {
		return createErrorResponse(e);
	}
	
	@ExceptionHandler(UserIdNotFoundException.class)
		protected ModelAndView UserIdNotFoundError(UserIdNotFoundException e){
			return createErrorResponse(e);
		
		}
	
	//얘가 예외가 발생하면 수행하는 핸들러
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView largeValueError(TooLargeValueException e) {
		return createErrorResponse(e);
	}
	@ExceptionHandler(InvalidArgumentsException.class)
	protected ModelAndView InvalidArgumentsError(InvalidArgumentsException e) {
		
		return createErrorResponse(e);
	}
	
	


}
