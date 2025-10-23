package com.kh.spring.board.model.mapper;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;

@Mapper
public interface BoardMapper {
	int selectTotalCount();
	List<BoardDTO> findAll(RowBounds rowBounds);
	
	int save(BoardDTO board);
	
	int increaseCount(Long boardNo);

	BoardDTO findByBoardNo(Long boardNo);
	
	BoardDTO findByBoardAndReply(Long boardNo);
	
	int insertReply(ReplyDTO reply);
}
