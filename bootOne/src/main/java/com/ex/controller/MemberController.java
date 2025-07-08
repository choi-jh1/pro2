package com.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.MemberDTO;
import com.ex.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mem/*") // http://localhost:8080/mem
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("main")
	public String main() {
		return "member/main";
	}

	@GetMapping("insert") // http://localhost:8080/mem/insert
	public String userInsert() {
		return "member/insertForm"; // user/insertForm.html <- templates 는 자동으로 붙음
	}

	@PostMapping("insert")
	public String userInsert(MemberDTO dto, Model model) {
		// DB 작업
		int result = memberService.memberInsert(dto);
		model.addAttribute("result", result);
		return "member/insertPro";
	}

	@GetMapping("login")
	public String login() {
		return "member/login";
	}

	@PostMapping("login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session) {

		// DB 처리
		int result = memberService.loginCheck(username, password);
		if (result == 1) {
			session.setAttribute("sid", username);
		}
		return "redirect:/mem/main"; // http://localhost:8080/mem/main 요청 : @GetMapping("main")
	}

	@GetMapping("myInfo")
	public String myInfo(HttpSession session, Model model) {
		String username = (String) session.getAttribute("sid");
		model.addAttribute("dto", memberService.myInfo(username));

		return "member/myInfo";
	}

	@GetMapping("allInfo")
	public String allInfo(Model model) {
		List<MemberDTO> list = memberService.allInfo();
		model.addAttribute("list", list);

		return "member/allInfo";
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/mem/main"; // @GetMapping("main")
	}

	/*
	 * @GetMapping("memberDelete") public String memberDelete() {
	 * 
	 * return "member/memberDelete"; }
	 * 
	 * 
	 * @PostMapping("memberDelete") public String
	 * memberDelete(@RequestParam("password") String password, HttpSession session )
	 * { String sid = (String)session.getAttribute("sid"); int result =
	 * memberService.memberDelete(sid, password);
	 * 
	 * if(result == 1) { session.invalidate(); } return "redirect:/mem/main"; }
	 */
	
	// 회원탈퇴
	@GetMapping("delete")
	public String memberDelete(HttpSession session) {
		// 로그인 된 사용자(세션) username으로 꺼냄
		String username = (String) session.getAttribute("sid");
		// db작업
		memberService.delete(username);
		// 세션삭제
		session.invalidate();
		// 메인으로 
		return "redirect:/mem/main";
	}
	
	// 1. 내 정보 (DB 꺼냄)
	@GetMapping("update")
	public String memberUpdate(HttpSession session, Model model) {
		// 로그인 된 사용자 꺼내기
		String username = (String)session.getAttribute("sid");
		// username 의 정보 꺼내기
		MemberDTO memberdto = memberService.myInfo(username);
		// view 로 정보 보내기
		model.addAttribute("dto", memberdto);
		// view
		return "member/updateForm";
	}
	// 2. 수정 	(DB 넣기)
	@PostMapping("update")
	public String memberUpdate(HttpSession session, MemberDTO memberDTO) {
		// 로그인 된 사용자 정보 -> 세션
		String username = (String)session.getAttribute("sid");
		memberDTO.setUsername(username);
		// DB 수정된 값 저장
		memberService.update(memberDTO);
		// main으로 보내기
		return "redirect:/mem/main";
	}
}




