package com.ex.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
import com.ex.service.ReporterService;
import com.ex.service.UsersService;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
	private final ReporterService reporterService;
	private final UsersService usersService;
	@GetMapping("adminPage")
	public String admin(HttpSession session) {
		String role = (String)session.getAttribute("role");
		if(role==null || !role.equals("admin")) {
			return "redirect:/user/main";
		}
		return "admin/adminPage";
	}
	
	@GetMapping("reporterRegister")
	public String reporterRegister() {
		return "admin/reporterRegister";
	}
	
	// 기자 관리
	@GetMapping("reporterUpdate")
	public String reportUpdate(Model model) {
		model.addAttribute("list",reporterService.reporterList());
		return "admin/reporterUpdate";
	}
	
	// 제보 관리
	@GetMapping("reportManagement")
	public String reportManagement() {
		return "admin/reportManagement";
	} 
	
	// 유저관리
	@GetMapping("userUpdate")
	public String userUpdate(Model model){
		model.addAttribute("list",usersService.userList());	
		return "admin/userUpdate";
	}
	// 유저 상태변경

	
	@GetMapping("articleCheck")
	public String articleCheck() {
		return "admin/articleCheck";
	}
	
	// 기자 회원가입
	@PostMapping("registerReporter")
	public String registerReporter(@ModelAttribute UsersDTO userDTO,
	                               @ModelAttribute ReporterDTO reporterDTO,
	                               @RequestParam("profile") MultipartFile profileFile) {

	    // 실제 저장 디렉토리
	    String uploadDir = "C:/upload/profile/";
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
	        // 실제 파일 저장
	        profileFile.transferTo(saveFile);

	        // DB에는 상대 경로만 저장
	        reporterDTO.setProfile_img("profile/" + newFileName);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error";
	    }

	    // reporter의 id는 user id와 동일
	    reporterDTO.setId(userDTO.getId());

	    // 서비스 계층에 전달
	    reporterService.reporterInsert(userDTO, reporterDTO);

	    return "redirect:/admin/adminPage";
	}
	
}
