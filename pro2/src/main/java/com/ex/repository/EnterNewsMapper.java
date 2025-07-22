package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.data.EnterNewsDTO;

@Mapper
public interface EnterNewsMapper {
	public List<EnterNewsDTO> getTop10DailyNews();
	
	public List<EnterNewsDTO> getPagedEnterNews(@Param("offset") int offset, @Param("limit") int limit);
	
	public List<EnterNewsDTO> getMostReadNews(@Param("limit") int limit);
	
	public int insertNews(EnterNewsDTO dto);
	
	public void increaseReadCount(int num);
	
	public EnterNewsDTO readEnterNews(int num);
	
	public void insertReadLog(@Param("newsId") int newsId, @Param("ip") String ip);

	public void softDelete(int num);
	
	public List<EnterNewsDTO> getNewsByCategory(@Param("category") String category);
	
	public List<EnterNewsDTO> getNewsByCategoryPaged(@Param("category") String category, @Param("offset") int offset, @Param("limit") int limit);

}
