package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NewsDTO {
	private int num;			// 글번호 (PK)
	private String title;		// 제목
	private String content;		// 글내용
	private String writer;		// 작성자
	private LocalDateTime reg;	// 작성 일시
	private int readCount;		// 조회수
	private int hot;			// 추천수
	private String category;	// 카테고리 (politics / economy / society)
	private String thumbUrl;	// 썸네일 이미지 URL
	private int is_breaking;	// 속보
	
	
	
	private String writerName;
	private String writerPhoto;
	private LocalDateTime mod;	// 수정 일시
	
}
