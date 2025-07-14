package com.ex.data;

import java.time.LocalDate;

import lombok.Data;

@Data	
public class ReportBoardDTO {
	private int reportId ; 		// 제보 번호
	private String title;  		// 제목
	private String content;		// 내용
	private String writerId;	// 작성자ID
	private String writerName;	// 작성자 닉네임
	private String writerPw;	// 작성자 비밀번호
	private LocalDate reg;		// 작성날짜
	private String status;		// 상태
	private String assigned;	// 담당기자
	private String email;		// 이메일
}
