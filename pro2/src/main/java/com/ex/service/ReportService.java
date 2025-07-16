package com.ex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.ReportBoardDTO;
import com.ex.repository.ReportMapper;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
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
            dto.setFileOriginalName(file.getOriginalFilename());
            dto.setFileSavedName(savedName);
            dto.setFileMimeType(file.getContentType());
            dto.setFileSize(file.getSize());
        } else {
            dto.setFileOriginalName("없음");
            dto.setFileSavedName("없음");
            dto.setFileMimeType("없음");
            dto.setFileSize(0L);
        }
        if (dto.getAssigned() == null || dto.getAssigned().isBlank()) {
            dto.setAssigned("미지정");
        }
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            dto.setStatus("대기중");
        }
        reportMapper.insertReport(dto);
    }
}
