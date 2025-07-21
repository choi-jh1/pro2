package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
	private int com_num;		// 댓글 번호 (PK)
	private String content;		// 댓글 내용
	private String writer;		// 작성자 ID
	private LocalDateTime reg;		// 댓글 작성 날짜
	private int ref;			// 그룹 번호
	private int re_level;		// 들여쓰기 (0:댓글, 1이상:답글)
	private int re_step;		// 정렬 순서
	private int is_delete;		// 삭제 여부
	private int num;			// 뉴스 번호
}
