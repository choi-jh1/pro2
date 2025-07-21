package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
@Mapper
@Repository
public interface ReporterMapper {
	ReporterDTO selectById(String id);
	
	// 기자 등록
	public void reporterInsert(ReporterDTO reporter);
	// 기자리스트(제보)
	public List<ReporterDTO> getReporterListWithStatus();
	// 기자 목록
	public List<UsersDTO> reporterList();
	// 기자 정보
	public ReporterDTO reporterInfo(String id);


	
	// 기자 정보 업데이트
	ReporterDTO findById(String id);
	public void update(ReporterDTO reporter);
	

	
	public void updateReporter(UsersDTO user);

	List<ReportBoardDTO> getAssignedReports(@Param("reporterId") String reporterId);


}