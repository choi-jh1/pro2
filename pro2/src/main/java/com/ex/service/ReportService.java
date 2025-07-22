package com.ex.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.ReportBoardDTO;
import com.ex.repository.ReportMapper;
import jakarta.mail.internet.MimeMessage;


import jakarta.mail.internet.MimeMessage;
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
    	
       // 1. DB 업데이트(제보 번호를 기준으로 담당기자배정)
        reportMapper.assignReporter(report_id, assigned);
        
        // 2. 메일 전송을 위한 제보글 정보 조회(익명인지, 회원인지 
        ReportBoardDTO dto = reportMapper.getReporterInfo(report_id);
        if(dto != null && dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
           String recipientType=dto.getWriter_id().equals("익명") ? "anonymous" : "users";
           sendAssignmentEmail(dto.getEmail(), dto.getTitle(), dto.getAssigned(), recipientType);
        }
        
        // 기자 이메일 전송
        if (dto != null && dto.getAssigned_Email() != null && !dto.getAssigned_Email().isBlank()) {
            sendAssignmentEmail(dto.getAssigned_Email(), dto.getTitle(), dto.getAssigned(), "reporter");
        }
    }
    
 // 메일 전송
    private void sendAssignmentEmail(String to, String title, String assigned, String recipientType) {
        try {
            // MimeMessage 객체 생성 (이메일 메시지의 틀)
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 메시지 작성 도우미 클래스: helper 객체를 통해 제목, 본문 등을 쉽게 작성
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // 수신자 이메일 주소 설정
            helper.setTo(to);

            // 발신자 이메일 주소 설정 (Gmail 계정과 일치해야 함)
            helper.setFrom("matpick2025@gmail.com"); // ※ Gmail SMTP 설정 시 필수

            // 이메일 제목 설정
            helper.setSubject("[TheFact 제보] 담당기자 배정 알림");

            // 수신자 유형에 따라 메일 본문 구성
            // recipientType: anonymous(익명 제보자), users(회원 제보자), reporter(기자 본인)
            String content = switch (recipientType) {
                case "anonymous" -> 
                    "제보하신 글 제목: " + title + "\n" +
                    "담당 기자: " + assigned + "\n\n" +
                    "현재 상태: 배정완료";
                case "users" -> 
                    "회원님의 제보글에 담당 기자가 배정되었습니다.\n\n" +
                    "제목: " + title + "\n" +
                    "담당 기자: " + assigned;
                case "reporter" -> 
                    "새로운 제보글이 배정되었습니다.\n\n" +
                    "제목: " + title + "\n" +
                    "제보 게시판을 확인해주세요.";
                default -> 
                    "알 수 없는 수신자 유형"; // 혹시 모를 예외 대응
            };

            // 메일 본문 텍스트 설정 (false = 일반 텍스트, true = HTML)
            helper.setText(content, false);

            // 메일 전송 실행
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            // 에러 발생 시 로그 출력 및 런타임 예외 발생
            e.printStackTrace();
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
    
    
    // 익명일 때 제보글 비번확인
    public ReportBoardDTO checkPw(String reportId, String password) {
       return reportMapper.checkPw(reportId, password);
    }
    
    // 제보글 상태변경
    public void updateStatus(int report_id, String status) {
    	reportMapper.updateStatus(report_id, status);
    }
    
    
}
