package com.ex.controller;

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
        reportService.insert(dto, file);
        return "redirect:/report/list";
    }

    @GetMapping("list")
    public String list() {
        return "report/list";
    }
}
