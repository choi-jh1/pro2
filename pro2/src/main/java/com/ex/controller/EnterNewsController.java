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

import com.ex.data.EnterNewsDTO;
import com.ex.data.UsersDTO;
import com.ex.service.EnterNewsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enter/*")
public class EnterNewsController {
	private final EnterNewsService enterNewsService;
	
	@GetMapping("main")
	public String enterMain(Model model) {
        List<EnterNewsDTO> top10 = enterNewsService.getTop10DailyNews();
        List<EnterNewsDTO> latestNews = enterNewsService.getPagedEnterNews(0, 10);

        model.addAttribute("top10", top10);
        model.addAttribute("latestNews", latestNews);
		
		return "enter/main";
	}
	
	@GetMapping("write")
	public String enterWriteForm(HttpSession session, RedirectAttributes redirect) {
	    String sid = (String) session.getAttribute("sid");
	    /*String role = (String) session.getAttribute("role");

	    if (sid == null || role == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(role)) {
	        redirect.addFlashAttribute("error", "기자만 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }*/

	    return "enter/write";
	}


	@PostMapping("write")
	public String enterWritePro(@ModelAttribute EnterNewsDTO dto, HttpSession session, RedirectAttributes redirect) {
	    String sid = (String) session.getAttribute("sid");
	    /*String role = (String) session.getAttribute("role");

	    if (sid == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(role)) {
	        redirect.addFlashAttribute("error", "기자만 글을 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }*/

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
	public String newsDetail(@RequestParam("num") int num, Model model) {
		// 1. 조회수 증가
		enterNewsService.increaseReadCount(num);
		
		// 2. 기사 상세 정보 조회
		EnterNewsDTO dto = enterNewsService.readEnterNews(num);
		model.addAttribute("dto", dto);
		
		return "enter/detail";
	}
}
