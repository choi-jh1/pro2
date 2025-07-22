package com.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.data.EnterNewsDTO;
import com.ex.data.NewsDTO;
import com.ex.data.SportsDTO;
import com.ex.data.UsersDTO;
import com.ex.service.EnterNewsService;
import com.ex.service.NewsService;
import com.ex.service.SportsService;
import com.ex.service.UsersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService usersService;
	private final SportsService sportsService;
	private final NewsService newsService;
	private final EnterNewsService enterNewsService;
	
	// 메인화면
	@GetMapping("main")
	public String main(Model model) {
		List<NewsDTO> newsList = newsService.newsReadCount();
		List<SportsDTO> sportsList = sportsService.sportsReadCount();
		List<EnterNewsDTO> enterList = enterNewsService.getMostReadNews(10);
		
		model.addAttribute("sportsList",sportsList);
		model.addAttribute("newsList",newsList);
		model.addAttribute("enterList", enterList);
		
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
	
	// 회원 정보 수정 페이지
	@GetMapping("update")
	public String update(Model model,HttpSession session) {
		String sid = (String)session.getAttribute("sid");
		model.addAttribute("dto",usersService.userInfo(sid));
		return "user/update";
	}
	// 회원 정보 수정 DB
	@PostMapping("update")
	public String update(UsersDTO dto,RedirectAttributes rttr) {
		int result = usersService.userUpdate(dto);
		rttr.addFlashAttribute("msg", "회원 정보가 수정되었습니다.");
		return "redirect:/user/myPage";
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
	// 비밀번호 변경 url
	@GetMapping("pwChange")
	public String pwChange(Model model, HttpSession session) {
		String sid = (String)session.getAttribute("sid");
		model.addAttribute("dto",usersService.userInfo(sid));
		return "user/pwChange";
	}
	// 비밀번호 변경 DB
	@PostMapping("pwChange")
	public String pwChange(UsersDTO dto,@RequestParam("pw1") String pw1,RedirectAttributes rttr) {
		int result = usersService.pwCheck(dto);
		if(result==1) {
			usersService.pwChange(pw1, dto.getId());
			rttr.addFlashAttribute("msg", "비밀번호가 수정되었습니다.");
		}else {
			rttr.addFlashAttribute("msg", "비밀번호를 확인해주세요.");
		}
		return "redirect:/user/myPage";
	}
}
