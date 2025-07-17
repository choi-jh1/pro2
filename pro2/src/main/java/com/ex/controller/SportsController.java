package com.ex.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.SportsDTO;
import com.ex.service.SportsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sports/*")
@RequiredArgsConstructor
public class SportsController {
	private final SportsService sportsService;
	
	// 스포츠기사 쓰기
	@GetMapping("write")
	public String write(Model model) {
		
		model.addAttribute("dto",sportsService.cate());
		return "sports/boardWrite";
	}
	// 기사 DB등록
	@PostMapping("write")
	public String write(SportsDTO dto) {
		sportsService.boardWrite(dto);
		
		return "redirect:/sports/list";
	}

	// 외부폴더에 이미지 생성
	@PostMapping("uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        String uploadPath = "C:/sports/upload/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadPath + fileName);
        try {
			file.transferTo(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}

        return "/upload/" + fileName; // 클라이언트가 사용할 URL
	}
	
	// 스포츠 메인
	@GetMapping("main")
	public String main(Model model) {
		model.addAttribute("list",sportsService.sportsList());
		return "sports/main";
	}
	
	// 스포츠 축구
	@GetMapping("list")
	public String list() {
		return "sports/list";
	}
	
	// 카테고리별 리스트 출력
	@GetMapping("boardList")
	public String boardList(Model model,@RequestParam("category") int category) {
		List<SportsDTO> list = sportsService.sportsCateList(category,0,5);
		int pageNum=1;
		model.addAttribute("list",list);
		model.addAttribute("cate",category);
		model.addAttribute("pageNum",pageNum);
		return "sports/boardList";
	}
	
	// 더보기 처리
	@GetMapping("more")
	@ResponseBody
	public List<SportsDTO> loadMore(@RequestParam("category") int category,@RequestParam("pageNum") int pageNum) {
	    int pageSize = 5; // 한 번에 5개씩
	    int offset = (pageNum - 1) * pageSize;
	    return sportsService.sportsCateList(category,offset,pageSize);
	}
}
