package com.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.EnterNewsDTO;
import com.ex.service.EnterNewsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enter")
public class EnterNewsController {
	private final EnterNewsService enterNewsService;
	
	@GetMapping("/main")
	public String enterMain(Model model) {
        List<EnterNewsDTO> top10 = enterNewsService.getTop10DailyNews();
        List<EnterNewsDTO> latestNews = enterNewsService.getPagedEnterNews(0, 10);

        model.addAttribute("top10", top10);
        model.addAttribute("latestNews", latestNews);
		
		return "enter/main";
	}
}
