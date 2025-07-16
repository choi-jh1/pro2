package com.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.ex.data.NewsDTO;
import com.ex.repository.NewsMapper;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper mapper;
    private static final int PAGE_SIZE = 10;

    /* 최신 5 */
    public List<NewsDTO> latestFive() {
        return mapper.selectLatest(5);
    }

    /* 글 저장 */
    public void insert(NewsDTO dto) {
        mapper.insertNews(dto);
    }

    /* --------- 옵션(속보·페이징) --------- */
    public List<NewsDTO> latestPage(int page) {
        Map<String, Object> m = new HashMap<>();
        m.put("limit", PAGE_SIZE);
        m.put("offset", (page - 1) * PAGE_SIZE);
        return mapper.selectLatestPage(m);
    }
    public boolean isLastPage(int page) {
        return page * PAGE_SIZE >= mapper.countAll();
    }
    
    public List<NewsDTO> getBreakingNews(int limit) {
        return mapper.selectBreakingByTitle(limit);
    }

}
