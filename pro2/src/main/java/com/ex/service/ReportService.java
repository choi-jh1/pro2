package com.ex.service;

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
    
}
