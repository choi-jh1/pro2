package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.EnterNewsDTO;
import com.ex.data.NewsDTO;
import com.ex.data.SportsDTO;
import com.ex.repository.ApproveMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApproveService {
	private final ApproveMapper approveMapper;
	
	public List<NewsDTO> getWaitNews(){
		return approveMapper.getWaitNews();
	}
	
	public List<EnterNewsDTO> getWaitEnterNews(){
		return approveMapper.getWaitEnterNews();
	}
	
	public List<SportsDTO> getSportsNews(){
		return approveMapper.getSportsNews();
	}
	
	public void approveNews(int num) {
		approveMapper.approveNews(num);
	}
	public void approveEnterNews(int num) {
		approveMapper.approveEnterNews(num);
	}
	
	public void approveSportsNews(int boardNum) {
		approveMapper.approveSportsNews(boardNum);
	}
}
