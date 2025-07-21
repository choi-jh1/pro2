package com.ex.data;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReportBoardDTO {
    private int report_id;		// 제보 번호
    private String title;		// 제보글 이름
    private String content;		// 제보글 내용
    private String writer_id;	// 제보자 아이디
    private String writer_Name;	// 제보자 닉네임
    private String writer_Pw;	// 제보자 비밀번호
    private LocalDateTime reg;		// 작성일
    private String status;		// 상태(대기중)
    private String assigned;	// 권한(기자)
    private String file_Original_Name;	// 파일 원래이름
    private String file_Saved_Name;		// db에 저장된이름
    private String file_Mime_Type;		// 타입
    private Long file_Size;				// 크기
    private String email;
	private String assigned_Email;
}
