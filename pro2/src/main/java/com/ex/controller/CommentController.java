package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.data.CommentDTO;
import com.ex.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
	private final CommentService commentService;
	
	// 댓글 등록
	@PostMapping("add")
	public String addComment(CommentDTO comment, HttpSession session) {
		String writer = (String) session.getAttribute("sid");
		
		if(writer == null) {
			return "redirect:/user/login";
		}
		comment.setWriter(writer);
		commentService.addComment(comment);
		return "redirect:/news/content/" + comment.getNum();
	}
	
	// 댓글 삭제
	@PostMapping("delete")
	public String deleteComment(@RequestParam("com_num") int com_num, @RequestParam("num") int num, HttpSession session) {
		String writer = (String) session.getAttribute("sid");
		if(writer == null) {
			return "redirect:/user/login";
		}
		commentService.deleteComment(com_num);
		return "redirect:/news/content/" + num;
	}
	
	// 답글 등록
	@PostMapping("reply")
	public String addReply(CommentDTO dto, HttpSession session, RedirectAttributes rttr) {
		String writer = (String) session.getAttribute("sid");
		if(writer == null) {
			return "redirect:/user/login";
		}
		// 1단계 답글까지만 허용
		if(dto.getRe_level() >= 1) {
			rttr.addFlashAttribute("error", "1단계 답글까지만 작성 가능합니다.");
			return "redirect:/news/content/" +dto.getNum();
		}
		// 답글 추가 전 기존 답글들의 re_step 증가
		commentService.updateReStep(dto);
		// 답글의 re_level은 원댓글 re_level + 1
		dto.setRe_level(dto.getRe_level() + 1);
		dto.setRe_step(dto.getRe_step() + 1);
		
		commentService.addReply(dto);
		
		return "redirect:/news/content/"+dto.getNum();
	}
	
}
