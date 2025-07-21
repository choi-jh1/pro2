package com.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
   public boolean isIdAvailable(String id) {
	   return usersMapper.findById(id) == null;
   }
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
   
   public ReporterDTO getReporterById(String reporterId) {
	return reporterMapper.findById(reporterId);
   }
   
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
   
   public void updateReporter(ReporterDTO reporter) {
	   reporterMapper.update(reporter);
   }
   
   public void updateUser(UsersDTO user) {
	   usersMapper.update(user);
   }
   




}