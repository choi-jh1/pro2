package com.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.data.EnterNewsDTO;
import com.ex.data.UsersDTO;
import com.ex.service.EnterNewsService;

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
	    UsersDTO user = (UsersDTO) session.getAttribute("member");
	    if (user == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(user.getRole())) {
	        redirect.addFlashAttribute("error", "기자만 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }
		return "enter/write";
	}
	
	@PostMapping("write")
	public String enterWritePro(@ModelAttribute EnterNewsDTO dto, HttpSession session, RedirectAttributes redirect) {
	    UsersDTO user = (UsersDTO) session.getAttribute("member");

	    if (user == null) {
	        return "redirect:/user/login";
	    }

	    if (!"reporter".equals(user.getRole())) {
	        redirect.addFlashAttribute("error", "기자만 글을 작성할 수 있습니다.");
	        return "redirect:/enter/main";
	    }

	    dto.setWriterId(user.getId());
	    enterNewsService.insertNews(dto);
	    
		return "redirect:/enter/main";
	}
}
