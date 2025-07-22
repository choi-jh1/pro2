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
    
    // 익명일때 비번받고 제보내용
    public ReportBoardDTO checkPw(@Param("report_id") String reportId, @Param("password") String password);

    // 메일 전송용(익명 and 회원)
    ReportBoardDTO getReporterInfo(@Param("report_id") int report_Id);

    List<ReportBoardDTO> getAssignedReports(@Param("reporterId") String reporterId);

    // 배정된 제보 상태변경
    public void updateStatus(@Param("report_id") int report_id, @Param("status") String status);
}
