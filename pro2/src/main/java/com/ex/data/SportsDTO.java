package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class SportsDTO {
	private int num;
	private String title;
	private String content;
	private String writer;
	private int cateId;
	private String thumbnail;
	private int readCount;
	private String status;
	private String cleanContent;
	private LocalDateTime reg;
	private String name;
	private int count;
}
