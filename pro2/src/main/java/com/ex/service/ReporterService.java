package com.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.data.EnterNewsDTO;
import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.SportsDTO;
import com.ex.data.UsersDTO;
import com.ex.repository.ReporterMapper;
import com.ex.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporterService {
   private final ReporterMapper reporterMapper;
   private final UsersMapper usersMapper;

   // 아이디 사용 가능 여부 확인
   public boolean isIdAvailable(String id) {
      return usersMapper.findById(id) == null;
   }
   // 회원가입 (Users + Reporter 동시 처리)
   @Transactional
   public void reporterInsert(UsersDTO user, ReporterDTO reporter) {
      if(!isIdAvailable(user.getId())) {
         throw new RuntimeException("이미 존재하는 아이디입니다.");
      }
      user.setRole("reporter");
      usersMapper.userInsert(user);
      reporter.setId(user.getId());
      reporterMapper.reporterInsert(reporter);
   }
   
   // 기자리스트(제보)
   public List<ReporterDTO> getReporterListWithStatus(){
      return reporterMapper.getReporterListWithStatus();
   }
   // 단일 기자 조회
   public ReporterDTO getReporterById(String reporterId) {
   return reporterMapper.findById(reporterId);
   }
   // 단일 유저 조회
   public UsersDTO getUserById(String id) {
      return usersMapper.findById(id);
   }
   
   // 기자 정보 조회/수정 (기자 마이페이지)
   public Map<String, Object> getReporterInfo(String id){
      UsersDTO user = usersMapper.findById(id);
      ReporterDTO reporter = reporterMapper.findById(id);
      Map<String, Object> result = new HashMap<>();
      result.put("user", user);
      result.put("reporter", reporter);
      return result;
   }
   
   // 기자 정보 업데이트
   public void updateReporter(ReporterDTO reporter) {
      reporterMapper.update(reporter);
   }
   // 유저 정보 업데이트
   public void updateUser(UsersDTO user) {
      usersMapper.update(user);
   }
   
   // 기자가 담당한 제보 목록 조회
   public List<ReportBoardDTO> getAssignedReports(String reporterId){
      return reporterMapper.getAssignedReports(reporterId);
   }
   
   // 스포츠에서 쓰는거임 (지우지 말 것)
   public ReporterDTO reporterInfo(String id) {
	   return reporterMapper.reporterInfo(id);
   }
   
   // 기자 이름 수정
   public void reporterId(String name,String id) {
	   reporterMapper.reporterId(name, id);
   }
   
   // 기자 정보
   public ReporterDTO selectById(String id) {
	   return reporterMapper.selectById(id);
   }
   
   // 내가 쓴 스포츠 기사
   public List<SportsDTO> selectSports(String writer) {
	   return reporterMapper.selectSports(writer);
   }
   // 내가 쓴 엔터 뉴스
   public List<EnterNewsDTO> selectEnter(String writer) {
	   return reporterMapper.selectEnter(writer);
   }
}