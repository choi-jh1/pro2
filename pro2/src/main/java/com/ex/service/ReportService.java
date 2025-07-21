package com.ex.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.ReportBoardDTO;
import com.ex.repository.ReportMapper;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
	// 제보 글 작성
    private final ReportMapper reportMapper;
    // 이메일 전송
    private final JavaMailSender mailSender;

    public void insert(ReportBoardDTO dto, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String uploadPath = "C:/upload/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            try {
                file.transferTo(new File(uploadDir, savedName));
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
            dto.setFile_Original_Name(file.getOriginalFilename());
            dto.setFile_Saved_Name(savedName);
            dto.setFile_Mime_Type(file.getContentType());
            dto.setFile_Size(file.getSize());
        } else {
            dto.setFile_Original_Name("없음");
            dto.setFile_Saved_Name("없음");
            dto.setFile_Mime_Type("없음");
            dto.setFile_Size(0L);
        }
        if (dto.getAssigned() == null || dto.getAssigned().isBlank()) {
            dto.setAssigned("미지정");
        }
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            dto.setStatus("대기중");
        }
        if (dto.getWriter_id()==null) {
        	dto.setWriter_id("익명");
        }
        reportMapper.insertReport(dto);
    }
    // 제보 글 목록
    public List<ReportBoardDTO> reportList(int start, int end){
    	return reportMapper.reportList(start, end);
    }
    
    // 제보 글 개수
    public int reportCount() {
    	return reportMapper.reportCount();
    }
    
    // 제보 글 내용
    public ReportBoardDTO reportContent(int report_id) {
    	ReportBoardDTO dto = reportMapper.reportContent(report_id);
    	return dto;
    }
    
    // 제보 글 담당기자에게 and 메일전송
    public void assignReporter(int report_id, String assigned) {
    	// 1. DB 수정
        reportMapper.assignReporter(report_id, assigned);
        
        // 2. 메일 전송을 위한 제보글 정보 조회
        ReportBoardDTO dto = reportMapper.getReporterInfo(report_id);
        if(dto != null && dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
        	String recipientType=dto.getWriter_id().equals("익명") ? "anonymous" : "users";
        	sendAssignmentEmail(dto.getEmail(), dto.getTitle(), assigned, recipientType);
        }
        
        // 기자 이메일 전송
        if (dto != null && dto.getAssigned_Email() != null && !dto.getAssigned_Email().isBlank()) {
            sendAssignmentEmail(dto.getAssigned_Email(), dto.getTitle(), assigned, "reporter");
        }
    }
    // 메일 전송
    private void sendAssignmentEmail(String to, String title, String assigned, String recipientType) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(to);
    	message.setSubject("[TheFact 제보] 담당기자 배정 알림");
    	
    	String body ="";
    	
    	switch (recipientType) {
    		case "anonymous":
    			body = "제보하신 글 제목 " + title + "\n" + "담당 기자: " + assigned + "\n"
    					+ "현재 상태: 배정완료\n\n" + "TheFact 제보센터 드림";
    			break;
    		case "users":
    			body = "회원님이 작성하신 제보글에 담당 기자가 배정되었습니다. \n\n"+ "제목 " + title + "\n" + "담당 기자: " + assigned + "\n"
    					+ "TheFact 제보센터 드림";	
    			break;
    		case "reporter":
    			body = "새로운 제보글이 배정되었습니다. \n\n" + "제보글 제목"+ title + "\n" + "담당해주실 기자님: " + assigned + "\n"
    					+ "제보 게시판을 확인해주세요";
    			break;
    	}
    	message.setText(body);
        mailSender.send(message);
    }
    
    
    // 익명일 때 제보글 비번확인
    public ReportBoardDTO checkPw(String reportId, String password) {
    	return reportMapper.checkPw(reportId, password);
    }
    
    
    
}
