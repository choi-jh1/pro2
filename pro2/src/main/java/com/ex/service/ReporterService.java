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
	private final ReporterMapper reporterMapper;
	@Autowired
	private UsersMapper userMapper;
	// 회원가입
	public void reporterInsert(UsersDTO user, ReporterDTO reporter) {
		user.setRole("reporter");
		userMapper.userInsert(user);
		reporterMapper.reporterInsert(reporter);
	}
	// 기자리스트(제보)
	public List<ReporterDTO> getReporterListWithStatus(){
		return reporterMapper.getReporterListWithStatus();
	}
	// 기자 목록 조회
	public List<UsersDTO> reporterList() {
		return reporterMapper.reporterList();
	}
	
	// 기자 정보 조회
	public ReporterDTO reporterInfo(String id) {
		return reporterMapper.reporterInfo(id);
	}

}
