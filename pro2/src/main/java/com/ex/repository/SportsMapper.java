package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.SportsCateDTO;
import com.ex.data.SportsDTO;

@Mapper
@Repository
public interface SportsMapper {
	// 스포츠기사 등록
	public int boardWrite(SportsDTO dto);
	// 스포츠기사 카테고리 출력
	public List<SportsCateDTO> cate();
	// 스포츠기사 목록
	public List<SportsDTO> sportsList();
}
