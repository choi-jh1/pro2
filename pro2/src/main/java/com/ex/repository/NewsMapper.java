<<<<<<< HEAD
package com.ex.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.data.NewsDTO;

@Mapper
public interface NewsMapper {
	
	// 메인 최신 5개 기사
	List<NewsDTO> selectLatest(@Param("limit") int limit);
	
	// 속보 3개 기사
	List<NewsDTO> selectBreaking(@Param("limit") int limit);
	
	// 최신 기사 목록 (페이징)
	List<NewsDTO> selectLatestPage(Map<String, Object> param);
	
	// 전체 글 수
	public int countAll();
}
=======
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
>>>>>>> main
