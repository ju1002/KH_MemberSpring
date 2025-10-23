package com.kh.spring.ajax.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;
@Slf4j //log.info 사용하기 위해 만듬
@Controller
@ResponseBody
public class AjaxController {

	//여기다 요청받아 처리해줄 requesthandler
	@GetMapping(value="test", produces="text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input")String value) {
		log.info("잘넘어옴:{}",value);
		
		
		
		
		/*응답할 데이터를 문자열로 반환
		 *  ModelAndView의 viewName필드에 return한 문자열값이 대입
		 *  =>DispatcherServlet => ViewResolver 이렇게 순서가 되는데
		 *  우리는 이렇게 답을 받고 싶지 않음 순수한 문자열로 받고 싶음
		 *  
		 *  반환하는 String 타입의 값이 View의 정보가 아닌 응답데이터라는 것으 ㄹ명시해서
		 *  ->MessageConverter라는 빈으로 이동하게끔
		 *  @ResponseBody
		 */
		
		String lunchMenu ="오늘 점심은 짬뽕이다!";
		
		return lunchMenu;
	}
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	@ResponseBody
	@PostMapping("replies")
	public String insertReply(ReplyDTO reply,HttpSession session) {
		
		boardService.insertReply(reply,session);
		
		log.info( "댓글 :{}", reply);
		int result = boardService.insertReply(reply, session);
		return result >0 ?"success":"fail";
	}
	
	@ResponseBody
	@GetMapping(value="board/{num}", produces="application/json; charsetUTF=8")
	public BoardDTO detail(@PathVariable(value="num")Long boardNo) {
		log.info("게시글 번호 잘 나옴?:{}",boardNo);
		BoardDTO board = boardService.findByBoardNo(boardNo);
		log.info("혹시 모르나 찍어봄 :{}",board);
	return board;	
	}
	
	
	
	
	
}
