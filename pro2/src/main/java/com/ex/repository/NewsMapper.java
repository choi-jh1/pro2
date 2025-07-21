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

    /* 속보 (제목 기준, limit 수 만큼) */
    List<NewsDTO> selectBreakingByTitle(@Param("limit") int limit);
    
    /* 페이징용 최신 기사 리스트 조회 */
    List<NewsDTO> selectLatestPage(Map<String, Object> param);
    
    // 전체 뉴스 수 카운트
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
