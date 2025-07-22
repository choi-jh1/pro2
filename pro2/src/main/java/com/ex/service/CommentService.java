package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.CommentDTO;
import com.ex.repository.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentMapper commentMapper;
	// 특정 뉴스 기사 번호에 대한 모든 댓글을 조회
	public List<CommentDTO> getComments(int num){
		return commentMapper.selectByNewsNum(num);
	}
	// 댓글 추가 후, 방금 추가한 댓글의 com_num을 기반으로 ref 컬럼을 업데이트
	public void addComment(CommentDTO comment) {
		commentMapper.insertComment(comment);
		commentMapper.updateRef(comment.getCom_num());
	}
	// 댓글 삭제
	public void deleteComment(int com_num) {
		commentMapper.deleteComment(com_num);	
	}
	// 대댓글 정렬 순서 갱신
	public void updateReStep(CommentDTO dto) {
		commentMapper.updateReStep(dto);
	}
	// 페이징된 댓글 목록 조회
	public List<CommentDTO> getCommentsPaged(int num, int offset, int pageSize){
		return commentMapper.selectCommentsPaged(num, offset, pageSize);
	}
	
	
	public String getWriterByCommentNum(int com_num) {
		return commentMapper.findWriterByCommentNum(com_num);
	}
}