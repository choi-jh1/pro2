package com.ex.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.data.SportsDTO;
import com.ex.service.ReporterService;
import com.ex.service.SportsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sports/*")
@RequiredArgsConstructor
public class SportsController {
	private final SportsService sportsService;
	private final ReporterService reporterService;
	
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
		
		return "redirect:/sports/main";
	}

	// 외부폴더에 이미지 생성
	@PostMapping("uploadImage")
	@ResponseBody

	public String uploadImage(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        String uploadPath = "C:/sports/upload/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadPath + fileName);
        if (!dest.exists()) {
        	dest.mkdirs(); // 디렉토리 없으면 생성
        }
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
		model.addAttribute("list",sportsService.sportsReaction());
		return "sports/main";
	}
	
	// 스포츠 축구
	@GetMapping("football")
	public String football(Model model) {
		int cate = 1;
		List<SportsDTO> list = sportsService.sportsCateList(cate,0,5);
		int pageNum=1;
		model.addAttribute("list",list);
		model.addAttribute("cate",cate);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("catename",sportsService.catename(cate));
		return "sports/sportsCategory";
	}
	// 스포츠 야구
	@GetMapping("baseball")
	public String baseball(Model model) {
		int cate = 2;
		List<SportsDTO> list = sportsService.sportsCateList(cate,0,5);
		int pageNum=1;
		model.addAttribute("list",list);
		model.addAttribute("cate",cate);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("catename",sportsService.catename(cate));
		return "sports/sportsCategory";
	}
	// 스포츠 농구
	@GetMapping("basketball")
	public String basketball(Model model) {
		int cate = 3;
		List<SportsDTO> list = sportsService.sportsCateList(cate,0,5);
		int pageNum=1;
		model.addAttribute("list",list);
		model.addAttribute("cate",cate);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("catename",sportsService.catename(cate));
		return "sports/sportsCategory";
	}
	// 스포츠 배구
	@GetMapping("volleyball")
	public String volleyball(Model model) {
		int cate = 4;
		List<SportsDTO> list = sportsService.sportsCateList(cate,0,5);
		int pageNum=1;
		model.addAttribute("list",list);
		model.addAttribute("cate",cate);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("catename",sportsService.catename(cate));
		return "sports/sportsCategory";
	}
	
	// 더보기 처리
	@GetMapping("more")
	@ResponseBody
	public List<SportsDTO> loadMore(@RequestParam("category") int category,@RequestParam("pageNum") int pageNum) {
	    int pageSize = 5; // 한 번에 5개씩
	    int offset = (pageNum - 1) * pageSize;
	    return sportsService.sportsCateList(category,offset,pageSize);
	}
	
	// 스포츠기사 내용
	@GetMapping("content/{num}")
	public String content(@PathVariable("num") int boardNum,Model model,HttpSession session) {
	    String sid = (String) session.getAttribute("sid");
	    String userReaction = null;

	    if (sid != null) {
	        userReaction = sportsService.reactionType(boardNum, sid);
	        model.addAttribute("userReaction", userReaction);
	        model.addAttribute("reactionType", userReaction);
	    } else {
	        model.addAttribute("userReaction", null);
	        model.addAttribute("reactionType", null);
	    }

	    // 좋아요 개수
	    Map<String, Integer> count = sportsService.reactionCount1(boardNum);

	    // 기사 정보
	    SportsDTO dto = sportsService.sportsContent(boardNum);
	    List<SportsDTO> list = sportsService.sportsReadCount();
	    
	    model.addAttribute("spList",sportsService.sportsReporter(dto.getWriter()));
	    model.addAttribute("list", list);
	    model.addAttribute("allReaction", sportsService.reactionAllCount(boardNum));
	    model.addAttribute("count", count);
	    model.addAttribute("repo", reporterService.reporterInfo(dto.getWriter()));
	    model.addAttribute("dto", dto);

	    return "sports/boardContent";
	}
	
	
	
	// 스포츠기사 좋아요
	@PostMapping("reaction")
	public ResponseEntity<?> addReaction(@RequestParam("boardNum") int num,@RequestParam("id") String id,@RequestParam("reactionType") String type) {
		
		sportsService.reactionInsert(num, id, type);
		return ResponseEntity.ok(Map.of("message","성공"));
		
	}
	
	// 반응 취소
	@DeleteMapping("reaction")
	public ResponseEntity<?> removeReaction(@RequestParam("boardNum") int num,@RequestParam("id") String id,@RequestParam("reactionType") String type){
		sportsService.removeReaction(num, id,type);
		return ResponseEntity.ok(Map.of("message", "반응 취소 성공"));
	}
	

}