package com.ex.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.data.CommentDTO;
import com.ex.data.EnterNewsDTO;
import com.ex.service.CommentService;
import com.ex.service.EnterNewsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enter/*")
public class EnterNewsController {
	private final EnterNewsService enterNewsService;
	private final CommentService commentService;

	
	@GetMapping("main")
	public String enterMain(Model model, HttpSession session) {
        List<EnterNewsDTO> top10 = enterNewsService.getTop10DailyNews();
        List<EnterNewsDTO> latestNews = enterNewsService.getPagedEnterNews(0, 10);

        model.addAttribute("top10", top10);
        model.addAttribute("latestNews", latestNews);
		
        model.addAttribute("sid", session.getAttribute("sid"));
        model.addAttribute("role", session.getAttribute("role"));
        
        model.addAttribute("type", "main");
		return "enter/main";
	}
	
	@GetMapping("write")
	public String enterWriteForm(HttpSession session, RedirectAttributes redirect) {
	    String sid = (String) session.getAttribute("sid");
	    String role = (String) session.getAttribute("role");

	    if (sid == null || role == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(role)) {
	        redirect.addFlashAttribute("error", "기자만 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }

	    return "enter/write";
	}


	@PostMapping("write")
	public String enterWritePro(@ModelAttribute EnterNewsDTO dto, HttpSession session, RedirectAttributes redirect) {
	    String sid = (String) session.getAttribute("sid");
	    String role = (String) session.getAttribute("role");

	    if (sid == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(role)) {
	        redirect.addFlashAttribute("error", "기자만 글을 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }

	    dto.setWriter_id(sid);
	    enterNewsService.insertNews(dto);

	    return "redirect:/enter/main";
	}

	@PostMapping("uploadImage")  
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
	    String uploadPath = "C:/upload/";  
	    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();  

	    File dest = new File(uploadPath + fileName);
	    try {
	        file.transferTo(dest);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "/upload/" + fileName;  // 썸네일 추출용 경로 그대로
	}

	@GetMapping("detail")
	public String newsDetail(@RequestParam("num") int num, HttpServletRequest request, HttpSession session, Model model) {

	    // 1. 조회수 증가
	    enterNewsService.increaseReadCount(num);

	    // 2. 클라이언트 IP 얻기
	    String ip = request.getRemoteAddr();

	    // 3. 읽기 로그 기록
	    enterNewsService.insertReadLog(num, ip);

	    // 4. 뉴스 상세 조회
	    EnterNewsDTO dto = enterNewsService.readEnterNews(num);
	    model.addAttribute("dto", dto);

	    // 5. 댓글 목록 조회
	    List<CommentDTO> commentList = commentService.getComments(num);
	    model.addAttribute("commentList", commentList);

	    // 6. 세션 정보 모델에 담기
	    model.addAttribute("sid", session.getAttribute("sid"));
	    model.addAttribute("role", session.getAttribute("role"));

	    return "enter/detail";
	}


	@PostMapping("/deletePro")
	public String deletePro(@RequestParam("num") int num, RedirectAttributes ra) {
	    enterNewsService.deleteNews(num);
	    ra.addFlashAttribute("msg", "삭제가 완료되었습니다.");
	    return "redirect:/enter/main";
	}


	@GetMapping("loadMore")
	@ResponseBody
	public List<EnterNewsDTO> loadMoreEnterNews(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
		return enterNewsService.getPagedEnterNews(offset, limit);
	}

	@GetMapping("category")
	public String categoryNews(@RequestParam("category") String category, Model model) {
		List<EnterNewsDTO> categoryNews = enterNewsService.getNewsByCategory(category);
		model.addAttribute("categoryNews", categoryNews);
		model.addAttribute("category", category);
		return "enter/category";
	}

	@GetMapping("/category/loadMore")
	@ResponseBody
	public List<EnterNewsDTO> getNewsByCategoryPaged(@RequestParam("category") String category,
			@RequestParam("offset") int offset, @RequestParam("limit") int limit) {

		return enterNewsService.getNewsByCategoryPaged(category, offset, limit);
	}

	// 연예뉴스에서 댓글 등록 처리
	@PostMapping("/comment")
	public String insertEnterComment(@RequestParam("num") int num, @RequestParam("content") String content, HttpSession session) {
		String writer = (String) session.getAttribute("sid");
		if (writer == null) {
			return "redirect:/user/login";
		}
		CommentDTO comment = new CommentDTO();
		comment.setNum(num); // 뉴스 번호
		comment.setContent(content);
		comment.setWriter(writer);
		comment.setRe_level(0);
		comment.setRe_step(0);
		commentService.addComment(comment);

		return "redirect:/enter/detail?num=" + num;
	}
	
	// 연예뉴스 대댓글
	@PostMapping("/reply")
	public String replyEnterComment(@ModelAttribute CommentDTO dto) {
		commentService.updateReStep(dto);   
		commentService.addComment(dto);     
		return "redirect:/enter/detail?num=" + dto.getNum();
	}

}
