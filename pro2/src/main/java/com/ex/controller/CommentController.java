package com.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		comment.setRe_level(0);
		comment.setRe_step(0);
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
	
	
	
	// 댓글 비동기 로드 (GET /comment/load?num=1&page=2)
	@GetMapping("load")
	@ResponseBody
	public List<CommentDTO> loadComments(@RequestParam("num") int num, @RequestParam("page") int page){
		int pageSize= 10;
		int offset = (page - 1) * pageSize;
		
		return commentService.getCommentsPaged(num, offset, pageSize);
	}
	
}
