package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;

public interface BoardService {

	//메소드 이름 붙이는 법 
	
	//게시글 목록 조회 + 페이징 처리
	
	//총게시글 수 조회
	//int selectListCount();
	//목록 조회
	Map<String,Object> findAll(Long boardNo);
	
	
	//게시글 작성
	int save(BoardDTO board,MultipartFile upfile,HttpSession session);
	
	
	
	
	
	//게시글 상세보기(조회수 증가)
	BoardDTO findByBoardNo(Long boardNo);
	
	
	
	//게시글 삭제하기
	int deleterByBoardNo(Long boardNo);
	
	
	
	//게시글 수정하기
	int update(BoardDTO board);
	
	
	//댓글 작성
	int insertReply(ReplyDTO reply,HttpSession sesison);
	
}
