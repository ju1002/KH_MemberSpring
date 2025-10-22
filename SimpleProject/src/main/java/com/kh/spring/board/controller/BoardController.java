package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor

public class BoardController {
	private final BoardService boardService;
	/*
	 * mapping
	 * 
	 *전체조회        == boards 		요청방식을 다르게 해서 구분가게 함
	 *상세조회(단일조회) ==boards/PK
	 *작성           == boards
	 * 
	 */
	
	
	@GetMapping("boards")
	public String findAll(@RequestParam(name="page",defaultValue="1")Long page
							,Model model)
	//키가 page의 값이 기본값으로 1로 넘어오게 만듬  page에 name과 value값을 담아서 사용
	{
		log.info("앞에서 넘어온 값 : {}", page);
		
		Map<String ,Object> map = boardService.findAll(page);
		model.addAttribute("map",map);
		
		return "board/list";
	}
	@GetMapping("boards/form")
	public String toForm() {
		return"board/form";
	}
	
	@PostMapping("boards")
	//multipartfile는 파일업로드를 처리할 때 사용하는 인터페이스 이다.
	public String save(BoardDTO board,MultipartFile upfile,HttpSession session)
	{log.info("게시글정보 : {},파일 정보 :{}",board,upfile);
	//첨부파일의 존재유무
	//MultipartFile객체의 fileName필드값으로 확인해야함
	
	//0.권한있는 요청인가 로그인이 있어야 하는데 그거는 session에 있음
	//1.파일 존재유무 체크 => 이름 바꾸기(파일 확장자 체크) => 있으면 파일 업로드
	//2.유효성 있는 값인가
	//3. 바뀐이름을 changeName필드에 담아서 Mapper로 보내기
	boardService.save(board,upfile,session);
	return "redirect:boards";
	}
	
	@GetMapping("boards/{id}")
	public String toDetail(@PathVariable(name="id") Long boardNo
					,Model model) {
		
		log.info("게시글번호 : {}",boardNo);
		//조회수 증가
		//조회수 증가에 성공했다면 SEECT를 조회
		//만악에 업는 게시물 번호라면 예외 발생
		
		
		BoardDTO board = boardService.findByBoardNo(boardNo);
		
		model.addAttribute("board",board);
		return"board/detail";
	}
	
	
	
	
	
	
	


}
