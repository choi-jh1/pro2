package com.ex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.NewsDTO;
import com.ex.data.ReporterDTO;
import com.ex.service.NewsService;
import com.ex.service.ReporterService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reporter/*")
@RequiredArgsConstructor
public class ReporterController {
	private final ReporterService reporterService;
	private final NewsService newsService;
	
	@GetMapping("myPage")
	public String myPage(HttpSession session, Model model) {
	   String reporterId = (String) session.getAttribute("sid");

	   Map<String, Object> info = reporterService.getReporterInfo(reporterId);
	   model.addAttribute("user", info.get("user"));
	   model.addAttribute("reporter", info.get("reporter"));

	   return "reporter/myPage";
	}

	
	@GetMapping("update")
	public String updateForm(HttpSession session, Model model) {
		String reporterId = (String) session.getAttribute("sid");
		ReporterDTO reporter = reporterService.getReporterById(reporterId);
		model.addAttribute("reporter", reporter);
		return "reporter/update";
	}
	@PostMapping("update")
	public String update(ReporterDTO dto, HttpSession session, Model model) {
		reporterService.updateReporter(dto);
		model.addAttribute("msg","수정이 완료되었습니다.");
		return "reporter/updateResult";
	};


	
	@GetMapping("myArticles")
	public String myArticles(HttpSession session, Model model) {
		String writer = (String) session.getAttribute("sid");	// 로그인 된 기자의 ID

		List<NewsDTO> list = newsService.getNewsByWriter(writer);
		model.addAttribute("newsList", list);
		
		return "reporter/myArticles";
	}
}
