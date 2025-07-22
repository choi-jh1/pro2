package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.EnterNewsDTO;
import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.SportsDTO;
import com.ex.data.UsersDTO;

@Mapper
@Repository
public interface ReporterMapper {
	// 기자 정보를 ID로 조회
	ReporterDTO selectById(String id);
	// 기자 등록 (users 테이블 + reporter 테이블 연동 필요)
	public void reporterInsert(ReporterDTO reporter);
	// status가 '기자'인 유저 목록만 조회 (제보 처리)
	public List<ReporterDTO> getReporterListWithStatus();
	// 전체 기자 목록 조회 (users 기준)
	public List<UsersDTO> reporterList();
	// 특정 기자의 상세 정보 조회 (기자 전용)
	public ReporterDTO reporterInfo(String id);
	// 특정 기자 정보를 조회
	ReporterDTO findById(String id);
	// 기자 정보 업데이트 (프로필, 카테고리 소개 등 수정)
	public void update(ReporterDTO reporter);
	// users 테이블의 기자 관련 정보 수정 (역할 변경)
	public void updateReporter(UsersDTO user);
	// 특정 기자에게 할당된 제보글 목록 조회
	List<ReportBoardDTO> getAssignedReports(@Param("reporterId") String reporterId);
	// 기자 이름 수정
	public void reporterId(@Param("name") String name,@Param("id") String id);
	// 내가 쓴 스포츠 기사
	public List<SportsDTO> selectSports(String writer);
	// 내가 쓴 엔터 기사
	public List<EnterNewsDTO> selectEnter(String writer);
}