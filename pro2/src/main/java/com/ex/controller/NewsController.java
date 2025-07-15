package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.service.NewsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news/*")  // http://localhost:8080/news
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("latestFive",    newsService.latestFive()); 
        model.addAttribute("breakingThree", newsService.breakingThree());
        model.addAttribute("latestPage1",   newsService.latestPage(1));
        model.addAttribute("lastPage",      newsService.isLastPage(1));

        return "news/home"; // templates/news/home.html로 이동
    }
}
