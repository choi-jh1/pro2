package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.data.CommentDTO;

@Mapper
public interface CommentMapper {
	
	// 댓글에 DB 삽입
	public void insertComment(CommentDTO comment);
	// insert 후 com_num을 re_ref로 업데이트
	public void updateRef(int com_num);
	// 특정 뉴스 번호(num)에 달린 모든 댓글 목록 조회
	public List<CommentDTO> selectByNewsNum(int num);
	// 댓글 번호(con_num)에 해당하는 댓글 삭제
	public void deleteComment(int com_num);
	// 페이징 처리를 위한 댓글 조회 (AJAX, 무한 스크롤)
	public List<CommentDTO> selectCommentsPaged(@Param("num") int num, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	public void updateReStep(CommentDTO dto);
	
	
	public String findWriterByCommentNum(int com_num);
}
