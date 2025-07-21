package com.ex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ex.data.NewsDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
import com.ex.service.NewsService;
import com.ex.service.ReporterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/reporter/*")
@RequiredArgsConstructor
public class ReporterController {

	private final ReporterService reporterService;
	private final NewsService newsService;
	
	// 기자 회원가입
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

	// 기자 마이페이지
	@GetMapping("myPage")
	public String myPage(HttpSession session, Model model) {
	   String id = (String) session.getAttribute("sid");
	   Map<String, Object> info = reporterService.getReporterInfo(id);
	   model.addAttribute("user", info.get("user"));
	   model.addAttribute("reporter", info.get("reporter"));

	   return "reporter/myPage";
	}

	// 기자 정보 조회
	@GetMapping("detail")
	public String detail(HttpSession session, Model model) {
		String id = (String) session.getAttribute("sid");
		Map<String, Object> info = reporterService.getReporterInfo(id);
		
		model.addAttribute("user", info.get("user"));
		model.addAttribute("reporter", info.get("reporter"));
		
		return "reporter/detail";
	}
	
	// 기자 정보 수정 폼
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
	
	// 정보 수정 처리
	@PostMapping("update")
	public String update(@ModelAttribute UsersDTO user, @ModelAttribute ReporterDTO reporter, Model model, HttpServletRequest request) {
		// DB에 업데이트
		reporterService.updateUser(user);
		reporterService.updateReporter(reporter);
		
		Map<String, Object> info = reporterService.getReporterInfo(user.getId());
		
		model.addAttribute("user", info.get("user"));
		model.addAttribute("reporter", info.get("reporter"));
		model.addAttribute("msg", "수정이 완료되었습니다.");
		
		// AJAX 요청인지 검사
	    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        // AJAX 요청일 땐 detail fragment 또는 전체 detail 뷰 반환 (페이지 전환 안함)
	        return "reporter/detail :: content-area"; // Thymeleaf fragment 지정, 없다면 그냥 "reporter/detail"
	    }
		
		return "redirect:/reporter/detail";
	}

	// 기자가 쓴 기사 목록
	@GetMapping("myArticles")
	public String myArticles(HttpSession session, Model model) {
		String writer = (String) session.getAttribute("sid");	// 로그인 된 기자의 ID

		List<NewsDTO> list = newsService.getNewsByWriter(writer);
		model.addAttribute("newsList", list);
		
		return "reporter/myArticles";
	}
}
