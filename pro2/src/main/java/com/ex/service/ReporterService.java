package com.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
import com.ex.repository.ReporterMapper;
import com.ex.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporterService {
   private final ReporterMapper reporterMapper;
   private final UsersMapper usersMapper;
   // 회원가입
   public void reporterInsert(UsersDTO user, ReporterDTO reporter) {
      user.setRole("reporter");
      usersMapper.userInsert(user);
      reporterMapper.reporterInsert(reporter);
   }
   // 기자리스트(제보)
   public List<ReporterDTO> getReporterListWithStatus(){
      return reporterMapper.getReporterListWithStatus();
   }
   
   public ReporterDTO getReporterById(String reporterId) {
	return reporterMapper.findById(reporterId);
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
   
   public void updateReporterInfo(UsersDTO user, ReporterDTO reporter) {
	   usersMapper.update(user);
	   reporterMapper.update(reporter);
   }
   public void updateReporter(ReporterDTO dto) {
	   reporterMapper.updateReporter(dto);
   }
}
