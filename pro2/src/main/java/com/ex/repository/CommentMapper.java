package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ex.data.CommentDTO;

@Mapper
public interface CommentMapper {
	
	// 댓글 추가
	public void insertComment(CommentDTO comment);
	// 댓글 목록 조회
	public List<CommentDTO> selectByNewsNum(int num);
	// 댓글 삭제
	public void deleteComment(int com_num);
	//
	public void updateReStep(CommentDTO dto);
	// 답글
	public void insertReply(CommentDTO dto);  

	
}
