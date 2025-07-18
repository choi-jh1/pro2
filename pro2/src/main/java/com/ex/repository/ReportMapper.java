package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.data.ReportBoardDTO;

@Mapper
public interface ReportMapper {
	// 글작성
    public void insertReport(ReportBoardDTO dto);
    
    // 글목록
    public List<ReportBoardDTO> reportList(@Param("start") int start, @Param("end") int end);
    
    // 글 개수
    public int reportCount();
    
    // 글 목록
    public ReportBoardDTO reportContent(int report_id);
    
    // 제보글 담당기자에게
    public void assignReporter(@Param("report_id") int report_id, @Param("assigned") String assigned);
}
