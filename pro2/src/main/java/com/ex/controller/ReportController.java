package com.ex.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.ReportBoardDTO;
import com.ex.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService reportService;
	
	@GetMapping("write")
	public String reportForm() {
		return "report/reportForm";
	}
	@PostMapping("writePro")
	public String reportWrite(ReportBoardDTO dto, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
		    String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		    try {
		        file.transferTo(new File("C:/upload/", savedName));
		    } catch (IOException e) {
		        throw new RuntimeException("파일 저장 실패", e);
		    }

		    dto.setFileOriginalName(file.getOriginalFilename());
		    dto.setFileSavedName(savedName);
		    dto.setFileMimeType(file.getContentType());
		    dto.setFileSize(file.getSize());
		} else {
		    dto.setFileOriginalName("없음");
		    dto.setFileSavedName("없음");
		    dto.setFileMimeType("없음");
		    dto.setFileSize(0L);  // 여기가 포인트
		}

	    reportService.insert(dto);

		
		return "redirect:/report/list";
	}
	@GetMapping("list")
	public String list() {
		return "report/list";
	}
}
