package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.EnterNewsDTO;
import com.ex.data.NewsDTO;
import com.ex.data.SportsDTO;

@Mapper
@Repository
public interface ApproveMapper {
	// 각각 게시판의 미승인 기사 목록
	public List<NewsDTO> getWaitNews();
	public List<EnterNewsDTO> getWaitEnterNews();
	public List<SportsDTO> getSportsNews();
	
	
	// 기사 승인 DB작업
	public int updateNewsStatus(int num);
	public int updateEntStatus(int num); 
	public int updateSportsStatus(int boardNum);
}
