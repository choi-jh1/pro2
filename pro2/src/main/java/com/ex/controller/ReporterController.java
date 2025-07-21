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
import com.ex.data.UsersDTO;
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
	
	@PostMapping("insert")
	public String insertReporter(UsersDTO user, ReporterDTO reporter, Model model) {
	    try {
	        reporterService.reporterInsert(user, reporter);
	        return "redirect:/user/login";
	    } catch (RuntimeException e) {
	        if ("이미 존재하는 아이디입니다.".equals(e.getMessage())) {
	            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
	            return "reporter/registerForm"; // 회원가입 폼 JSP 경로에 맞게 변경
	        }
	        throw e;  // 다른 예외는 그대로 던짐
	    }
	}


	
	@GetMapping("myPage")
	public String myPage(HttpSession session, Model model) {
	   String id = (String) session.getAttribute("sid");
	   Map<String, Object> info = reporterService.getReporterInfo(id);
	   model.addAttribute("user", info.get("user"));
	   model.addAttribute("reporter", info.get("reporter"));

	   return "reporter/myPage";
	}
	
	@GetMapping("update")
	public String updateForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("sid");
		Map<String, Object> info = reporterService.getReporterInfo(id);
		
		if(info.get("user") == null) {
		    throw new RuntimeException("해당 사용자가 존재하지 않습니다: " + id);
		}
		if(info.get("reporter") == null) {
		    throw new RuntimeException("해당 기자 정보가 존재하지 않습니다: " + id);
		}

		
		model.addAttribute("user", info.get("user"));
		model.addAttribute("reporter", info.get("reporter"));
		
		return "reporter/update";
	}
	
	@PostMapping("update")
	public String update(UsersDTO user, ReporterDTO reporter, Model model) {
		reporterService.updateUser(user);
		reporterService.updateReporter(reporter);
		model.addAttribute("msg", "수정이 완료되었습니다.");
		return "redirect:/reporter/update";
	}


	
	@GetMapping("myArticles")
	public String myArticles(HttpSession session, Model model) {
		String writer = (String) session.getAttribute("sid");	// 로그인 된 기자의 ID

		List<NewsDTO> list = newsService.getNewsByWriter(writer);
		model.addAttribute("newsList", list);
		
		return "reporter/myArticles";
	}
}
