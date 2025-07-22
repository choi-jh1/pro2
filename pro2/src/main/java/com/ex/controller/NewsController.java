package com.ex.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ex.data.CommentDTO;
import com.ex.data.NewsDTO;
import com.ex.service.CommentService;
import com.ex.service.NewsService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news/*")  // http://localhost:8080/news
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final CommentService commentService;

    // 뉴스 메인 페이지
    // 각 카테고리별 상위 5개 기사
    // 속보 3개 기사
    @GetMapping("home")
    public String home(Model model) {
    	List<NewsDTO> politicsTop5 = newsService.getTop5ByCategory("politics");
    	List<NewsDTO> economyTop5 = newsService.getTop5ByCategory("economy");
    	List<NewsDTO> societyTop5 = newsService.getTop5ByCategory("society");
    	List<NewsDTO> breakingNews = newsService.getBreakingNews();
        model.addAttribute("politicsTop5", politicsTop5);
        model.addAttribute("economyTop5", economyTop5);
        model.addAttribute("societyTop5", societyTop5);
        model.addAttribute("breakingNews", breakingNews);
        
        return "news/home"; // news/home.html로 이동
    }
    
    // 기사 작성 폼 반환
    // 기자가 아닌 경우 메인으로 리다이렉트
    @GetMapping("write")
    public String writeForm(HttpSession session) {
    	String role = (String) session.getAttribute("role");
    	if(!"reporter".equals(role)) {
    		return "redirect:/user/main";
    	}
    	return "news/write";
    }
    
    // 기사 작성 처리
    // 본문에서 첫 이미지 추출하여 썸네일 지정
    @PostMapping("writePro")
    public String writePro(@ModelAttribute NewsDTO dto, HttpSession session) {
    	System.out.println("category = "+dto.getCategory());
    	String role = (String) session.getAttribute("role");
    	if(!"reporter".equals(role)) {
    		return "redirect:/user/main";
    	}
    	
    	String writerId = (String) session.getAttribute("sid");
    	dto.setWriter(writerId);
    	
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
    // HTML 본문에서 첫번째 이미지의 src 추출
    private String extractFirstImageSrc(String html) {
    	if (html == null || html.isBlank()) return null;
    	
    	Document doc = Jsoup.parse(html);   // 써머노트 HTML 파싱
    	Element img  = doc.selectFirst("img[src]"); // 첫번째 이미지
    	
    	return img != null ? img.attr("src") : null; // src 반환
    }
    
    // 정치 카테고리 뉴스 목록 페이지 반환
    @GetMapping("politics")
    public String politicsPage(Model model) {
    	List<NewsDTO> politicsList = newsService.getPoliticsNews();
    	model.addAttribute("newsList", politicsList);
    	return "news/politics";
    }
    // 경제 카테고리 뉴스 목록 페이지 반환
    @GetMapping("economy")
    public String economyPage(Model model) {
    	List<NewsDTO> economyList = newsService.getEconomyNews();
    	model.addAttribute("newsList", economyList);
    	return "news/economy";
    }
    
    // 사회 카테고리 뉴스 목록 페이지 반환
    @GetMapping("society")
    public String societyPage(Model model) {
    	List<NewsDTO> societyList = newsService.getSocietyNews();
    	model.addAttribute("newsList", societyList);
    	return "news/society";
    }
    
    // 최신 뉴스 페이징 처리 (AJAX 요청 API)
    // @Param page : 요청한 페이지 번호
    // @return : 해당 페이지의 뉴스 목록
    @GetMapping("latestPage")
    @ResponseBody
    public List<NewsDTO> latestPate(@RequestParam("page") int page){
    	return newsService.latestPage(page);
    }
    
    // 뉴스 상세 페이지 반환
    // 기사 정보와 해당 댓글 목록 포함
    // @Param num : 뉴스 번호
    @GetMapping("content/{num}")
    public String content(@PathVariable("num") int num, Model model) {
    	newsService.newsReadCountUp(num);
        NewsDTO news = newsService.getNewsByNum(num);
        List<CommentDTO> commentList = commentService.getComments(num);
        model.addAttribute("news", news);
        model.addAttribute("commentList", commentList);
        return "news/content";
    }

    // 뉴스 추천 수 증가 처리
    @PostMapping("hot")
    public String increaseHot(@RequestParam("num") int num) {
    	newsService.increaseHot(num);
    	return "redirect:/news/content/" + num;
    }
}