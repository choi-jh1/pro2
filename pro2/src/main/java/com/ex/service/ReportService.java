package com.ex.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.data.ReportBoardDTO;
import com.ex.repository.ReportMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReportService {
	private final ReportMapper reportMapper;
	
	@Transactional 
	public void insert(ReportBoardDTO dto) {
		
	}
}
