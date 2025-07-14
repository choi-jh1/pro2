package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.NewsDTO;

@Mapper
@Repository
public interface NewsMapper {
	// 메인 뉴스
	public NewsDTO mainNews();
}
