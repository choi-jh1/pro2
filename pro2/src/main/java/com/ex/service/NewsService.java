package com.ex.service;

import java.util.Collections;
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
	
	/*카테고리 <정치><경제><사회>*/
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
	
	// 카테고리 별 조회수 상위 5개 기사 조회
	public List<NewsDTO> getTop5ByCategory(String category) {
        return mapper.selectTop5ByCategory(category);
	}
	
	// 속보 3개 기사 조회
	public List<NewsDTO> getBreakingNews() {
		return mapper.selectBreakingByTitle(3);
	}
	
	// 최신 뉴스 페이지별 조회 (페이징 처리)
	public List<NewsDTO> latestPage(int page){
		int offset = (page - 1) * page_size;
		Map<String, Object> param = new HashMap<>();
		param.put("offset", offset);
		param.put("limit", page_size);
		return mapper.selectLatestPage(param);
	}
	
	// 전체 글 수 기준 마지막 페이지 여부 체크
	public boolean isLastPage(int page) {
		int total = mapper.countAll();
		return page * page_size >= total;
	}

	// 뉴스 상세 조회
	public NewsDTO getNewsByNum(int num) {
		return mapper.selectByNum(num);
	}
	
	// 추천 수 증가
	public void increaseHot(int num) {
		mapper.increaseHot(num);
		
	}
	
	// 작성자 기준 뉴스 조회
	public List<NewsDTO> getNewsByWriter(String writer){	
		if(writer == null || writer.isBlank()) {
			return Collections.emptyList();
		}
		return mapper.selectByWriter(writer);
	}
	
	
	// 조회수 증가
	public void newsReadCountUp(int num) {
		mapper.newsReadCountUp(num);
	}
	
	// 조회수 순 뉴스 리스트 조회
	public List<NewsDTO> newsReadCount(){
		return mapper.newsReadCount();
	}
	
	// 뉴스 기사 등록
	public void insert(NewsDTO dto) {
		mapper.insertNews(dto);
	}
}