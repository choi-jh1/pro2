package com.ex.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ex.data.NewsDTO;
import com.ex.repository.NewsMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class NewsService {
	private final NewsMapper mapper;
	private static final int page_size = 10;
	
	// 메인 최신 5개 기사
	public List<NewsDTO> selectLatest() {
        return mapper.selectLatest();
	}
	
	// 속보 3개 기사
	public List<NewsDTO> getBreakingNews() {
		return mapper.selectBreakingByTitle(3);
	}
	
	// 최신 목록
	public List<NewsDTO> latestPage(int page){
		int offset = (page - 1) * page_size;
		Map<String, Object> param = new HashMap<>();
		param.put("offset", offset);
		param.put("limit", page_size);
		return mapper.selectLatestPage(param);
	}
	
	public boolean isLastPage(int page) {
		int total = mapper.countAll();
		return page * page_size >= total;
	}


	// 정치 기사 페이지
	public List<NewsDTO> getPoliticsNews(){
		return mapper.selectByCategory("politics");
	}
	// 경제 기사 페이지
	public List<NewsDTO> getEconomyNews() {
		return mapper.selectByCategory("economy");
	}
	// 사회 기사 페이지
	public List<NewsDTO> getSocietyNews() {
		return mapper.selectByCategory("society");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 뉴스 저장
	public void insert(NewsDTO dto) {
		mapper.insertNews(dto);
	}
}
