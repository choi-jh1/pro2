package com.ex.service;


import com.ex.data.NewsDTO;
import com.ex.repository.NewsMapper;

public class NewsService {
	private NewsMapper newsMapper;
	
	public NewsDTO mainNews() {
		return newsMapper.mainNews();
	}
}
