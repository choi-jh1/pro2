package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.BoardDTO;
import com.ex.repository.BoardMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardMapper boardMapper;
	
	public void insert(BoardDTO dto) {
		int maxNum = boardMapper.maxNum();
		if(dto.getNum() != 0) { // 글번호가 있다 = 답글인 경우
			boardMapper.reStepUp(dto);
			dto.setRef(dto.getNum());
			dto.setRe_step(dto.getRe_step()+1);
			dto.setRe_level(dto.getRe_level()+1);
		}else { //  새 글인 경우
			dto.setRef(maxNum+1);
		}
		boardMapper.boardInsert(dto);
	}
	
	public int count() {
		return boardMapper.boardCount();
	}
	
	public List<BoardDTO> list(int start, int end){
		return boardMapper.boardList(start, end);
	}
	
	public BoardDTO content(int pageNum, int num) {
		return boardMapper.content(pageNum, num);
	}
}











