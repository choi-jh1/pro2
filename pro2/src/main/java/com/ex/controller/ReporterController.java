package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.ReporterDTO;
import com.ex.service.ReporterService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reporter/*")
@RequiredArgsConstructor
public class ReporterController {
	private final ReporterService reporterService;
	
	@GetMapping("myPage")
	public String myPage(HttpSession session, Model model) {
		String reporterId = (String) session.getAttribute("sid");
		ReporterDTO reporter = reporterService.getReporterById(reporterId);
		
		model.addAttribute("reporter", reporter);
	
	
	return "reporter/myPage";
	}
}
