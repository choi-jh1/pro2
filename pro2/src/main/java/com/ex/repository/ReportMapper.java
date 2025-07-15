package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.ReportBoardDTO;


@Mapper
@Repository
public interface ReportMapper {
	// 글쓰기
	public void insertReport(ReportBoardDTO dto);
	
}
