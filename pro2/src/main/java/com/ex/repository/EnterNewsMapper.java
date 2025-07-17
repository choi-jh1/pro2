package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.data.EnterNewsDTO;

@Mapper
public interface EnterNewsMapper {
	public List<EnterNewsDTO> getTop10DailyNews();
	
	public List<EnterNewsDTO> getPagedEnterNews(@Param("offset") int offset, @Param("limit") int limit);
	
	public int insertNews(EnterNewsDTO dto);
}
