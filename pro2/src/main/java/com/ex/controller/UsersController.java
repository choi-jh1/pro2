package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

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
	public String login(UsersDTO dto,HttpSession session,Model model) {
		UsersDTO user = usersService.loginCheck(dto);
		if(user != null) {
			session.setAttribute("sid",user.getId());
			session.setAttribute("role",user.getRole());
			return "redirect:/user/main";
		}else {
			model.addAttribute("loginFail",true);
			return "user/login";
		}
		
	}

	// 회원가입 창
	@GetMapping("insert")
	public String userInsert() {
		return "user/insertForm";
	}
	// 아이디 중복확인
	@GetMapping("userCheck")
	@ResponseBody
	public String userCheck(@RequestParam("username") String username) {
		int check = usersService.idCheck(username);
		if(check==1) {
			return "사용 불가능한 아이디입니다.";
		}else {
			return "사용 가능한 아이디입니다.";
		}
	}
	// 회원가입 DB
	@PostMapping("insert")
	public String userInsert(UsersDTO dto) {
		usersService.userInsert(dto);
		return "redirect:/user/main";
	}
	// 아이디 찾기 주소
	@GetMapping("findId")
	public String findId() {
		return "user/findId";
	}
	// 아이디 찾기
	@PostMapping("findId")
	@ResponseBody
	public String findId(UsersDTO dto) {
		String id = usersService.findId(dto);
		if(id==null) {
			return "아이디를 찾을수 없습니다.";
		}else {
			return "아이디는 "+id+" 입니다.";
		}
	}
	// 비밀번호 재설정 유저 확인
	@GetMapping("pwCheck")
	public String pc() {
		return "user/pwCheck";
	}
	// 비밀번호 재설정 유저 확인
	@PostMapping("pwCheck")
	@ResponseBody
	public String rc(UsersDTO dto) {
		UsersDTO user = usersService.userCheck(dto);
		if(user != null) {
			return dto.getId();
		}else {
			return "";
		}
	}
	// 비밀번호 재설정 주소
	@GetMapping("resetPw")
	public String rp(@RequestParam("id") String id, Model model) {
	    model.addAttribute("id", id); // 뷰로 전달
	    return "user/resetPw";
	}
	// 비밀번호 재설정
	@PostMapping("pwUpdate")
	public String pwUp(@RequestParam("id") String id,@RequestParam("pw") String pw) {
		System.out.println(id+pw);
		usersService.pwUpdate(pw,id);
		
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
