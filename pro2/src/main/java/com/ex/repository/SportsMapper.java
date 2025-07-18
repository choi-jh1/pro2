package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
	// 스포츠기사 카테고리 이름 출력
	public String catename(int id);
	// 스포츠기사 목록
	public List<SportsDTO> sportsList();
	// 카테고리별 스포츠기사 목록
	public List<SportsDTO> sportsCateList(@Param("cateId") int cateId,@Param("offset") int offset,@Param("pageSize") int size);
	// 내용 출력 이미지 제외
	public List<String> content();
	// 스포츠기사 내용 출력
	public SportsDTO sportsContent(int boardNum);
}
