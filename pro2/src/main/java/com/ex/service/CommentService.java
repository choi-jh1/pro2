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
	
	public List<CommentDTO> getComments(int num){
		return commentMapper.selectByNewsNum(num);
	}

	// 댓글
	public void addComment(CommentDTO comment) {
		commentMapper.insertComment(comment);
		commentMapper.updateRef(comment.getCom_num());
		
	}
	// 댓글 삭제
	public void deleteComment(int com_num) {
		commentMapper.deleteComment(com_num);	
	}
	// 
	public void updateReStep(CommentDTO dto) {
		commentMapper.updateReStep(dto);
	}

	
	public List<CommentDTO> getCommentsPaged(int num, int offset, int pageSize){
		return commentMapper.selectCommentsPaged(num, offset, pageSize);
	}

}
