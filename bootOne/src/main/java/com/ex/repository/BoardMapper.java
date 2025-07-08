package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.BoardDTO;
@Repository
@Mapper
public interface BoardMapper {
	// 최대 글번호
	public int maxNum();
	// 답글 위치
	public void reStepUp(BoardDTO dto);
	// 글 작성
	public void boardInsert(BoardDTO BoardDTO);
	// 글 개수
	public int boardCount();
	// 글 목록 조회
	public List<BoardDTO> boardList(@Param("start") int start, @Param("end") int end);
	// 글 내용
	public BoardDTO content(@Param("pageNum") int pageNum, @Param("num") int num);
}
