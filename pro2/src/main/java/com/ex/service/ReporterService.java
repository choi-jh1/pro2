package com.ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
import com.ex.repository.ReporterMapper;
import com.ex.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporterService {
   private final ReporterMapper repoterMapper;
   @Autowired
   private UsersMapper userMapper;
   // 회원가입
   public void reporterInsert(UsersDTO user, ReporterDTO reporter) {
      user.setRole("reporter");
      userMapper.userInsert(user);
      repoterMapper.reporterInsert(reporter);
   }
   // 기자리스트(제보)
   public List<ReporterDTO> getReporterListWithStatus(){
      return repoterMapper.getReporterListWithStatus();
   }
   public ReporterDTO getReporterById(String reporterId) {
	// TODO Auto-generated method stub
	return null;
   }

}
