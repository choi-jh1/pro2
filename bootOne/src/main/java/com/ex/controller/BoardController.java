package com.ex.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import com.ex.data.BoardDTO;
import com.ex.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board/*") // http://localhost:8080/board/*
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("list")
	public String list(Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum) {
		int pageSize=10;
		int currentPage = pageNum;
		int start = (currentPage -1) * pageSize +1;
		int end = currentPage * pageSize;
		int count = boardService.count();
		
		List<BoardDTO> list = null;
		if(count > 0) {
			list = boardService.list(start, end);
		}else {
			list = Collections.emptyList();
		}
		
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
		int startPage= (int)((currentPage -1)/10) * 10+1;
		int pageBlock= 10;
		int endPage= startPage+pageBlock -1;
		if( endPage > pageCount ) {
			endPage = pageCount;
		}
		
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("pageBlock", pageBlock);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@GetMapping("write")
	public String write(BoardDTO boardDTO) {
		
		return "board/write";
	}
	
	@PostMapping("write")
	public String write2(BoardDTO boardDTO) {
		boardService.insert(boardDTO);
		return "redirect:/board/list";
	}
	
	@GetMapping("content")
	public String content(Model model,@RequestParam("pageNum") int pageNum, @RequestParam("num") int num) {
	    BoardDTO dto = boardService.content(pageNum, num);
	    model.addAttribute("dto", dto);
	    return "board/content";
	}
	
}








