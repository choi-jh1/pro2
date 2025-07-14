package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.ReportBoardDTO;
import com.ex.data.ReportBoardFileDTO;

@Mapper
@Repository
public interface ReportMapper {
	public void insertReport(ReportBoardDTO dto);
	public void insertReportBoardFile(ReportBoardFileDTO fdto);
}
