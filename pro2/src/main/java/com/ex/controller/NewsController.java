package com.ex.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import com.ex.data.NewsDTO;
import com.ex.service.NewsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news/*")  // http://localhost:8080/news
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("home")
    public String home(Model model) {
    	// 카테고리 별 조회수 상위 5개 뉴스
    	List<NewsDTO> politicsTop5 = newsService.getTop5ByCategory("politics");
    	List<NewsDTO> economyTop5 = newsService.getTop5ByCategory("economy");
    	List<NewsDTO> secietyTop5 = newsService.getTop5ByCategory("society");
        model.addAttribute("politicsTop5", politicsTop5);
        model.addAttribute("economyTop5", economyTop5);
        model.addAttribute("secietyTop5", secietyTop5);
        
        // 속보 3개
        List<NewsDTO> breakingNews = newsService.getBreakingNews();
        model.addAttribute("breakingNews", breakingNews);
        
        return "news/home"; // news/home.html로 이동
    }
    
    // 기사 작성
    @GetMapping("write")
    public String writeForm() {
    	return "news/write";	// news/write.html 이동 
    }
    @PostMapping("writePro")
    public String writePro(@ModelAttribute NewsDTO dto) {

        // 본문에서 첫 이미지 src 추출
        String thumbnail = extractFirstImageSrc(dto.getContent());
        if (thumbnail != null) {
            dto.setThumbUrl(thumbnail);
        } else {
            dto.setThumbUrl("/images/default.jpg"); // 이미지 없을 때 기본값
        }
        
        // writer 누락 시 임시 지정
        if (dto.getWriter() == null || dto.getWriter().isBlank()) {
            dto.setWriter("관리자");
        }

        newsService.insert(dto);
        return "redirect:/news/home";
    } 
    // NewsController 클래스 안쪽 아무 곳 (writePro 아래 등) 에 넣어주세요
    private String extractFirstImageSrc(String html) {
    	if (html == null || html.isBlank()) return null;
    	
    	Document doc = Jsoup.parse(html);   // 써머노트 HTML 파싱
    	Element img  = doc.selectFirst("img[src]"); // 첫번째 이미지
    	
    	return img != null ? img.attr("src") : null; // src 반환
    }
    
    
    // 정치 카테고리 페이지
    @GetMapping("politics")
    public String politicsPage(Model model) {
    	List<NewsDTO> politicsList = newsService.getPoliticsNews();
    	model.addAttribute("newsList", politicsList);
    	return "news/politics";
    }
    
    // 경제 카테고리 페이지
    @GetMapping("economy")
    public String economyPage(Model model) {
    	List<NewsDTO> economyList = newsService.getEconomyNews();
    	model.addAttribute("newsList", economyList);
    	return "news/economy";
    }
    
    // 사회 카테고리 페이지
    @GetMapping("society")
    public String societyPage(Model model) {
    	List<NewsDTO> societyList = newsService.getSocietyNews();
    	model.addAttribute("newsList", societyList);
    	return "news/society";
    }
    
    @GetMapping("latestPage")
    @ResponseBody
    public List<NewsDTO> latestPate(@RequestParam("page") int page){
    	return newsService.latestPage(page);
    }
    
    
    
    

}



















