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
	
	// 댓글 등록 처리
	@PostMapping("add")
	public String addComment(CommentDTO comment, HttpSession session) {
		String writer = (String) session.getAttribute("sid");	// 로그인 사용자 확인
		
		if(writer == null) {
			return "redirect:/user/login";		// 로그인 안되어 있으면 로그인 페이지 이동
		}
		comment.setWriter(writer);		// 작성자
		comment.setRe_level(0);			// 기본 댓글 : 깊이 0
		comment.setRe_step(0);			// 기본 댓글 : 정렬 0
		commentService.addComment(comment);	//  서비스에 등록 요청
		return "redirect:/news/content/" + comment.getNum();	// 기사 상세 페이지로 이동
	}
	
	// 댓글 삭제 처리
	@PostMapping("delete")
	public String deleteComment(@RequestParam("com_num") int com_num, @RequestParam("num") int num, HttpSession session) {
		String writer = (String) session.getAttribute("sid");	// 로그인 확인
		if(writer == null) {
			return "redirect:/user/login";		// 로그인 안되어 있으면 로그인 페이지 이동
		}
		String commentWriter = commentService.getWriterByCommentNum(com_num);
		// 본인이 아니라면 삭제하지 못함
		if(!writer.equals(commentWriter)) {
			return "redirect:/news/content/" + num + "?error=unauthorized";
		}
		commentService.deleteComment(com_num);	// 댓글 삭제
		return "redirect:/news/content/" + num;	// 기사 상세 페이지로 리다이렉트
	}
	
	// 댓글 비동기 로딩 (AJAX 요청)
	@GetMapping("load")
	@ResponseBody
	public List<CommentDTO> loadComments(@RequestParam("num") int num, @RequestParam("page") int page){
		int pageSize= 10;						// 한 페이지에 10개
		int offset = (page - 1) * pageSize;		// 페이지 오프셋 계산
		
		return commentService.getCommentsPaged(num, offset, pageSize);	// 페이징 댓글 리스트 반환
	}	
}