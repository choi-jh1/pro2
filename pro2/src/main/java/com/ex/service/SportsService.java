package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;
import com.ex.repository.SportsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportsService {
	private final SportsMapper sportsMapper;
	
	// 스포츠기사 작성
	public int boardWrite(SportsDTO dto) {
		return sportsMapper.boardWrite(dto);
	}
	
	// 스포츠기사 카테고리
	public List<SportsCateDTO> cate() {
		return sportsMapper.cate();
	}
	
	// 스포츠기사 목록
	public List<SportsDTO> sportsList(){
		return sportsMapper.sportsList();
	}
	
}
