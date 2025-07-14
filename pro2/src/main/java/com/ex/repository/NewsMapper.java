package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ex.data.NewsDTO;

@Mapper
public interface NewsMapper {
	// 메인 뉴스
	public NewsDTO mainNews();
}
