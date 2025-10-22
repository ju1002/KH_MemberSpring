package com.kh.spring.board.model.service;


import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.utll.PageInfo;
import com.kh.spring.utll.Pagenation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor //final로 선언된 필드의 매개변수 생성자를 자동으로 생성
public class BoardServiceImpl implements BoardService {

	//@Override
//	public int selectListCount() {
//		return 0;
//	}

	private final BoardMapper boardMapper;
	private final Pagenation pagenation;
	@Override
	public Map<String,Object> findAll(Long page) {
		Map<String , Object> map = new HashMap();
		List<BoardDTO> boards = new ArrayList();
		//유효성 검증
		if(page<1) {
			throw new InvalidArgumentsException("잘못된 접근입니다.");
		}
		int count  = boardMapper.selectTotalCount();
		log.info("총 게시글 개수 :{}",count);
		
		PageInfo pi = pagenation.getPageInfo(count,page.intValue(),5,5);
		
		if(count>0) {
			RowBounds rb =new RowBounds((page.intValue()-1)*5,5);
			boards = boardMapper.findAll(rb);
			
		}
		map.put("pi", pi);
		map.put("boards", boards);
		
		return map;
	}
	private void validateUser(BoardDTO board, HttpSession session) {
		
		String boardWriter=board.getBoardWriter();
		
		MemberDTO loginMember=((MemberDTO)session.getAttribute("loginMember"));
		
		if(loginMember==null || !boardWriter.equals(loginMember.getUserId())) {
			throw new AuthenticationException("권한없는 접근입니다");
		}
	}
		private void validateContent(BoardDTO board) {
		if(board.getBoardTitle().trim().isEmpty()||board.getBoardContent().trim().isEmpty()) {
			throw new InvalidArgumentsException("유효하지 않은 요청입니다.");
		}
		
		String boardTitle = board.getBoardTitle().replaceAll("<", "$lt");
		String boardContent = board.getBoardContent().replaceAll("<","&lt");
		if(board.getBoardTitle().contains("배주영 ㅄ")) {
			boardTitle= board.getBoardTitle().replaceAll("배주영 ㅄ", "너 고소");
			
		}
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
	}
	@Override
	public int save(BoardDTO board,MultipartFile upfile,HttpSession session) {
		//1. 권한 검증
		validateUser(board,session);
		//2번 유효성 검증
		validateContent(board);
		//3번 파일이 있을 경우 업로드==> 빈으로 분리
		if(!upfile.getOriginalFilename().isEmpty()) {
			//이름 바꾸기
			//KH_시간+숫자+원본확장자
			StringBuilder sb = new StringBuilder();
			sb.append("KH_");
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			sb.append(currentTime);
			sb.append("_");
			int num = (int)(Math.random()*900)+100;
			sb.append(num);
			String ext = upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf("."));
			sb.append(ext);
		
		//-------------------------------------------------------------------------
		ServletContext application = session.getServletContext();
		String savePath = application.getRealPath("/resources/files/");
		try {
			upfile.transferTo(new File(savePath + sb.toString()));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		board.setOriginName(upfile.getOriginalFilename());
		board.setChangeName("/spring/resources/files/"+sb.toString());
		}
		//애 책임 분리하기 까지가 커트라인 => 총대가 여기까지
		
		int result =boardMapper.save( board);
		
		
		if(result!=1) {
			//이럴 때 발생시킬 예외 하나 만들기
			throw new RuntimeException("이게 왜 이래?");
		}
		
		return result;
		
		
	
	}



	public BoardDTO findByBoardNo(Long boardNo) {
		if(boardNo<1) {
			throw new InvalidArgumentsException("유효하지 않는값입니다.");
			
		}
		int result = boardMapper.increaseCount(boardNo);
		
		if(result !=1) {
			throw new InvalidArgumentsException("잘못된 요청입니다.");
		}
		BoardDTO board= boardMapper.findByBoardNo(boardNo);
		if(board==null) {
			throw new InvalidArgumentsException("삭제된게시글입니다.");
			
		}
		
		
		
		return  board;
	
	}
	
	
	
	@Override
	public int deleterByBoardNo(Long boardNo) {
		return 0;
	}
	@Override
	public int update(BoardDTO board) {
		return 0;
	}
}
