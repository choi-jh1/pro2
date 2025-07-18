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
		
	}
	// 댓글 삭제
	public void deleteComment(int com_num) {
		commentMapper.deleteComment(com_num);	
	}
	// 
	public void updateReStep(CommentDTO dto) {
		commentMapper.updateReStep(dto);
	}
	// 답글
	public void addReply(CommentDTO dto) {
		// re_level이 1 이상이면 답글 추가 제한
		if(dto.getRe_level() >= 1) {
			throw new IllegalArgumentException("1단계 답글까지만 허용 가능합니다.");
		}
		// 기존 답글 추가 전 re_step 업데이트
		updateReStep(dto);
		
		commentMapper.insertReply(dto);
	}

}
