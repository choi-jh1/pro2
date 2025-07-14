package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/main/*")
public class NewsController {

	@GetMapping("main")
	public String write2() {
		
		return "user/main";
	}
}
