package com.ex.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.service.ReportService;
import com.ex.service.ReporterService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ReporterService reporterService;
    // 제보 글쓰기 폼
    @GetMapping("write")
    public String reportForm(HttpSession session) {
    	String role = (String)session.getAttribute("role");
    	if(role == null || (!role.equals("admin") && !role.equals("reporter"))) {
    		return "report/reportForm";		
    	}
    	return "redirect:/report/list";
    }
    // 제보 글쓰기
    @PostMapping("writePro")
    public String reportWrite(ReportBoardDTO dto, @RequestParam("file") MultipartFile file, HttpSession session) {
 
        reportService.insert(dto, file);
        return "redirect:/report/list";
    }
    // 제보 글 목록
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
    // 제보 내용
    @GetMapping("content")
    public String content(Model model,
                          @RequestParam("report_id") int report_id,
                          @RequestParam("pageNum") int pageNum,
                          HttpSession session) {

        String sid = (String) session.getAttribute("sid");
        ReportBoardDTO dto = reportService.reportContent(report_id);

        // reporter는 로그인한 경우만 조회
        String category = null;
        if (sid != null) {
            ReporterDTO reporter = reporterService.getReporterById(sid);
            if (reporter != null) {
                category = reporter.getCategory();
            }
        }

        model.addAttribute("dto", dto);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("category", category); // null일 수도 있음
        return "report/reportContent";
    }
	// 제보 익명일 떄 비밀번호
	@PostMapping("checkPw")
	public ResponseEntity<String> checkPw (@RequestParam("report_id") String reportId,
	                                       @RequestParam("password") String password){
	    try {
	        ReportBoardDTO dto = reportService.checkPw(reportId, password);
	        if(dto != null) {
	            return ResponseEntity.ok("success"); // 비밀번호 일치
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail"); // 비밀번호 틀림
	        }
	    } catch(Exception e) {
	        e.printStackTrace(); // 문제 디버깅을 위해 로그 남기기
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error"); // 진짜 예외 발생 시
	    }
	}
	// 카테고리별 작성 링크
	@GetMapping("reportWrite")
	public String reportWrite(@RequestParam("report_id") int report_id, @RequestParam("category") String category, HttpSession session) {
		
		 reportService.updateStatus(report_id, category);
		 switch (category) {
	        case "일반":
	            return "redirect:/news/write?reportId=" + report_id;
	        case "스포츠":
	            return "redirect:/sports/write?reportId=" + report_id;
	        case "연예":
	            return "redirect:/enter/write?reportId=" + report_id;
	        default:
	            return "redirect:/error/invalidCategory";
	    }
	};

} 
