package com.ex.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ex.data.NewsDTO;

@Mapper
public interface NewsMapper {

    /* 메인 최신 5개 */
	List<NewsDTO> selectLatest();

    /* 글 저장 */
    int insertNews(NewsDTO dto);

    /* --------- 옵션(속보·페이징) --------- */
    List<NewsDTO> selectBreakingByTitle(@Param("limit") int limit);
    List<NewsDTO> selectLatestPage(Map<String, Object> param);
    int countAll();
    
    /* <정치> 기사 최신순 */
    List<NewsDTO> selectPolitics();
}
