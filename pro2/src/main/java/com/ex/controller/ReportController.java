package com.ex.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String reportWrite(ReportBoardDTO dto, @RequestParam MultipartFile file) {
        reportService.insert(dto, file);
        return "redirect:/report/list";
    }

    @GetMapping("list")
    public String list(Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum) {
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1)*pageSize+1;
        int end = currentPage * pageSize;
        int reportCount = reportService.reportCount();
        
        List<ReportBoardDTO> list = null;
    	if(reportCount > 0 ) {
    		list = reportService.reportList(start, end);
    	}else {
    		list = Collections.emptyList();
    	}
    	int pageCount = reportCount/pageSize+(reportCount%pageSize==0 ? 0:1);
    	int startPage=(int)((currentPage-1)/10)*10+1;
    	int pageBlock=10;
    	int endPage=startPage+pageBlock-1;
    	if(pageCount<endPage) {
    		endPage=pageCount;
    	}
        model.addAttribute("pageCount",pageCount);
    	model.addAttribute("startPage",startPage);
    	model.addAttribute("pageBlock",pageBlock);
    	model.addAttribute("endPage",endPage);
    	model.addAttribute("pageSize",pageSize);
    	model.addAttribute("pageNum",pageNum);
    	model.addAttribute("start",start);
    	model.addAttribute("end",end);
    	model.addAttribute("reportCount",reportCount);
    	model.addAttribute("list",list);
    	
    	return "report/list";
    }
    	
	@GetMapping("content")
	public String content(Model model, @RequestParam("report_id") int report_id, @RequestParam("pageNum") int pageNum) {
		ReportBoardDTO dto = reportService.reportContent(report_id);
		model.addAttribute("dto", dto);
		model.addAttribute("pageNum",pageNum);
		return "report/reportContent";
    }
}
