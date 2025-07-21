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
	
	public void updateNewsStatus(int num) {
		approveMapper.updateNewsStatus(num);
	}
	public void updateEntStatus(int num) {
		approveMapper.updateEntStatus(num);
	}
	
	public void updateSportsStatus(int boardNum) {
		approveMapper.updateSportsStatus(boardNum);
	}
}
