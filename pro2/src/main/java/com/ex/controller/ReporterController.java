package com.ex.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.EnterNewsDTO;
import com.ex.data.NewsDTO;
import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.SportsDTO;
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
		ReporterDTO reporter = (ReporterDTO) info.get("reporter");

		
		model.addAttribute("user", info.get("user"));
		model.addAttribute("reporter", reporter);
	
		return "reporter/update";
	}
	
	// 정보 수정 처리
	@PostMapping("update")
	public String update(@ModelAttribute UsersDTO user, Model model, @RequestParam("profile_img") MultipartFile profileFile, HttpServletRequest request) {
		// DB에 업데이트 // 실제 저장 디렉토리
		ReporterDTO reporter = new ReporterDTO();
		reporter.setId(user.getId());
	    reporter.setWriter_name(request.getParameter("writer_name"));
		reporter.setIntro(request.getParameter("intro"));
	    String uploadDir = "C:/profile/upload";
	    File uploadPath = new File(uploadDir);
	    
	    
	    
	    if (!uploadPath.exists()) {
	        uploadPath.mkdirs(); // 디렉토리 없으면 생성
	    }

	    // 원본 파일명
	    String originalFileName = profileFile.getOriginalFilename();

	    // 확장자 추출 (예: .jpg, .png)
	    String ext = "";
	    int dotIndex = originalFileName.lastIndexOf(".");
	    if (dotIndex != -1) {
	        ext = originalFileName.substring(dotIndex);
	    }

	    // UUID로 중복 방지 파일명 생성
	    String uuid = UUID.randomUUID().toString();
	    String newFileName = uuid + ext;

	    // 저장 파일 객체 생성
	    File saveFile = new File(uploadPath, newFileName);
		    try {
		    	if (profileFile == null || profileFile.isEmpty()) {
		    	    reporter.setProfile_img("/upload/default-profile.png");
		    	} else {
		    	    profileFile.transferTo(saveFile);
		    	    reporter.setProfile_img("/upload/" + newFileName);
		    	}
			    } catch (Exception e) {
			        e.printStackTrace();
			        return "error";
			    }
		reporterService.updateReporter(reporter);
		
		Map<String, Object> info = reporterService.getReporterInfo(user.getId());
		reporterService.reporterId(reporter.getWriter_name(),reporter.getId() );
		model.addAttribute("user", info.get("user"));
		model.addAttribute("reporter", info.get("reporter"));
		model.addAttribute("msg", "수정이 완료되었습니다.");
		
		
		
		return "redirect:/reporter/myPage";
	}

	// 기자가 쓴 기사 목록
	@GetMapping("myArticles")
	public String myArticles(HttpSession session, Model model) {
		String writer = (String) session.getAttribute("sid");	// 로그인 된 기자의 ID
		ReporterDTO dto = reporterService.selectById(writer);
		if((dto.getCategory()).equals("일반")) {
			List<NewsDTO> list = newsService.getNewsByWriter(writer);
			model.addAttribute("newsList", list);
		}else if(dto.getCategory().equals("스포츠")) {
			List<SportsDTO> list = reporterService.selectSports(writer);
			model.addAttribute("newsList", list);
		}else if(dto.getCategory().equals("연예")) {
			List<EnterNewsDTO> list = reporterService.selectEnter(writer);
			model.addAttribute("newsList", list);
		}
	
		
		return "reporter/myArticles";
	}
	
	// 내 제보 관리 페이지 요청
	@GetMapping("myReports")
	public String myReports(HttpSession session, Model model) {
		String reporterId = (String) session.getAttribute("sid");
		if(reporterId == null) {
			return "redirect:/user/login";
		}
		List<ReportBoardDTO> assignedReports = reporterService.getAssignedReports(reporterId);
		model.addAttribute("assignedReports", assignedReports);
		return "reporter/myReports";
	}
}