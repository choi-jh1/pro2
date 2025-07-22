package com.ex.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.ReportBoardDTO;
import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
import com.ex.service.ApproveService;
import com.ex.service.ReportService;
import com.ex.service.ReporterService;
import com.ex.service.UsersService;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
	private final ReporterService reporterService;
	private final ReportService reportService;
	private final UsersService usersService;
	private final ApproveService approveService;
	@GetMapping("adminPage")
	public String admin(HttpSession session) {
		String role = (String)session.getAttribute("role");
		if(role==null || !role.equals("admin")) {
			return "redirect:/user/main";
		}
		return "admin/adminPage";
	}
	
	@GetMapping("reporterRegister")
	public String reporterRegister() {
		return "admin/reporterRegister";
	}
	
	// 기자 관리
	@GetMapping("reporterUpdate")
	public String reportUpdate(Model model) {
		model.addAttribute("list",usersService.reporterList());
		return "admin/reporterUpdate";
	}
	
	// 제보 관리(제보 글 목록과 비슷)
	@GetMapping("reportManagement")
	public String reportManagement(Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum) {
		int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1)*pageSize+1;
        int end = currentPage * pageSize;
        int reportCount = reportService.reportCount();
        List<ReportBoardDTO> list = null;
        List<UsersDTO> reportList = usersService.reporterList();
        model.addAttribute("reporterList", reportList);
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
    
    	
		return "admin/reportManagement";
	} 
	
	// 유저관리
	@GetMapping("userUpdate")
	public String userUpdate(Model model){
		model.addAttribute("list",usersService.userList());	
		return "admin/userUpdate";
	}
	// 유저 상태변경
	@PostMapping("updateStatus")
	public ResponseEntity<String> updateStatus(@RequestParam("userId") String userId, @RequestParam("status") String status) {
		int result = usersService.updateStatus(userId, status);
		if(result > 0) {
			return ResponseEntity.ok("success");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
		}
	}
	
	// 기자 카테고리 변경
	@PostMapping("updateCategory")
	public ResponseEntity<String> updateCategory(@RequestParam("id") String id, @RequestParam("category") String category){
		int reselt = usersService.updateCategory(id, category);
		if(reselt > 0) {
			return ResponseEntity.ok("success");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
		}
	}
	// 기사 미승인 글들 가져오기
	@GetMapping("approveNews")
	public String showApproveNews(Model model) {
	    model.addAttribute("newsList", approveService.getWaitNews());
	    return "admin/approveNews";
	}

	@GetMapping("approveEnt")
	public String showApproveEnt(Model model) {
	    model.addAttribute("entList", approveService.getWaitEnterNews());
	    return "admin/approveEnt";
	}

	@GetMapping("approveSports")
	public String showApproveSports(Model model) {
	    model.addAttribute("sportsList", approveService.getSportsNews());
	    return "admin/approveSports";
	}
	
	// 뉴스 상태 변경
	@PostMapping("approveNews")
	public String updateNewsStatus(@RequestParam("num") int num) {
		approveService.updateNewsStatus(num);
		return "redirect:/admin/adminPage";
		
	}


	// 연예 상태 변경
	@PostMapping("approveEnt")
	public String updateEntStatus(@RequestParam("num") int num) {
		approveService.updateEntStatus(num);
		return "redirect:/admin/adminPage";
		
	}

	// 스포츠 상태 변경
	@PostMapping("approveSports")
	public String updateSportsStatus(@RequestParam("num") int num) {
		approveService.updateSportsStatus(num);
		return "redirect:/admin/adminPage";
		
	}

	
	
	// 기자 회원가입
	@PostMapping("registerReporter")
	public String registerReporter(@ModelAttribute UsersDTO userDTO, @ModelAttribute ReporterDTO reporterDTO) {
	    // reporter의 id는 user id와 동일
	    reporterDTO.setId(userDTO.getId());
	    // 서비스 계층에 전달
	    reporterService.reporterInsert(userDTO, reporterDTO);
	    return "redirect:/admin/adminPage";
	}
	
	// 기자리스트(제보)
	@GetMapping("/admin/reporterList")
	@ResponseBody
	public List<ReporterDTO> reporterListJson(){
		return reporterService.getReporterListWithStatus();
	}
	// 제보게시판 기자배정
	@PostMapping("assign")
	public ResponseEntity<String> assignReporter(@RequestParam("report_id") int report_id, @RequestParam("assigned") String assigned) {
		reportService.assignReporter(report_id, assigned);
		 try {
		        reportService.assignReporter(report_id, assigned);
		        return ResponseEntity.ok("success");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
		    }
	}

}
