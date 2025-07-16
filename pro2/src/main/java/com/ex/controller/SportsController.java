package com.ex.controller;

import java.io.File;
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
		
		String thumbnail = firstImg(dto.getContent());
		dto.setThumbnail(thumbnail);
		
		sportsService.boardWrite(dto);
		
		return "redirect:/sports/boardWrite";
	}
	// 첫번째 이미지 추출
	public String firstImg(String img) {
		if(img == null) {
			return null;
		}
		
		Pattern pattern = Pattern.compile("<img[^>]+src=['\\\"]([^'\\\"]+)['\\\"]");
		Matcher matcher = pattern.matcher(img);
		
		if(matcher.find()) {
			return matcher.group(1);	// 첫 번째 이미지
		}
		return null;
	}
	// 외부폴더에 이미지 생성
	@PostMapping("uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        String uploadPath = "D:/cjh/upload/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadPath + fileName);
        try {
			file.transferTo(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}

        return "/upload/" + fileName; // 클라이언트가 사용할 URL
	}
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("list",sportsService.sportsList());
		return "sports/list";
	}
}
