package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnterNewsDTO {
	private int num;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime reg;
	private int readCount;
	private String category;
	private int hot;
}


