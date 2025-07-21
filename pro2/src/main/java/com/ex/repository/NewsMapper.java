package com.ex.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ex.data.NewsDTO;

@Mapper
public interface NewsMapper {

	/* <정치><경제><사회> 기사 최신순 */
	List<NewsDTO> selectByCategory(@Param("category") String category);
	
    /* 메인 기사 (각 카테고리 별 5개)(조회수 순) */
	List<NewsDTO> selectTop5ByCategory(String category);

    /* --------- 옵션(속보·페이징) --------- */
    List<NewsDTO> selectBreakingByTitle(@Param("limit") int limit);
    List<NewsDTO> selectLatestPage(Map<String, Object> param);
    int countAll();

    // 기사 상세 페이지
    NewsDTO selectByNum(int num);
    // 추천 수 : 기사 상세 페이지 content
    void increaseHot(int num);

    // 기자 마이페이지 <내가 쓴 기사 조회>
    List<NewsDTO> selectByWriter(String writer);
    
    
    
    
    
    
	/* 글 저장 */
	int insertNews(NewsDTO dto);


}
