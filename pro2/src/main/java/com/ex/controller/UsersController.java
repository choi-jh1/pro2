package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.UsersDTO;
import com.ex.service.UsersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService usersService;
	
	// 메인화면
	@GetMapping("main")
	public String main() {
		return "user/main";
	}
	
	// 로그인 창
	@GetMapping("login")
	public String login() {
		return "user/login";
	}
	
	// 로그인 확인
	@PostMapping("login")
	public String login(UsersDTO dto,HttpSession session) {
		UsersDTO user = usersService.loginCheck(dto);
		if(user != null) {
			session.setAttribute("sid",user.getId());
			session.setAttribute("role",user.getRole());
		}
		
		return "redirect:/user/main";
	}

	// 회원가입 창
	@GetMapping("insert")
	public String userInsert() {
		return "user/insertForm";
	}
	
	// 회원가입 DB
	@PostMapping("insert")
	public String userInsert(UsersDTO dto) {
		usersService.userInsert(dto);
		return "redirect:/user/main";
	}
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/main";
	}
	
	// 마이페이지
	@GetMapping("myPage")
	public String myPage(HttpSession session) {
		String role = (String) session.getAttribute("role");
		
		if("reporter".equals(role)) {
			return "redirect:/reporter/myPage";	// 기자용 마이페이지 이동
		}
		
		return "user/myPage";	// 일반 회원 마이페이지 이동
	}
	
	// 회원 정보 수정
	@GetMapping("update")
	public String update() {
		return "user/update";
	}
	
	// 회원 탈퇴
	@GetMapping("delete")
	public String delete() {
		return "user/delete";
	}
	// 회원 탈퇴(비밀번호 받음)
	@PostMapping("delete")
	public String userDelete(HttpSession session, @RequestParam("pw") String pw) {
		String sid = (String)session.getAttribute("sid");
		int result = usersService.userDelete(sid, pw);
		if(result == 1) {
			session.invalidate();
		}
		return "redirect:/user/main";
	}
}
