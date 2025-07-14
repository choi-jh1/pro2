package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.NewsDTO;
import com.ex.service.NewsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news/*")	// http://localhost:8080/news/*
@RequiredArgsConstructor
public class NewsController {
	private NewsService newsService;
	
	@GetMapping("main")
	public String newsMain(Model model) {
		NewsDTO mainNews = newsService.mainNews();
		model.addAttribute("mainNews",mainNews);
		return "news/list";
	}
}
