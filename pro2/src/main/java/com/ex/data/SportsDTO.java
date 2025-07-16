package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class SportsDTO {
	private int boardNum;
	private String title;
	private String content;
	private int cateId;
	private String thumbnail;
	private int readCount;
	private LocalDateTime reg;
}
