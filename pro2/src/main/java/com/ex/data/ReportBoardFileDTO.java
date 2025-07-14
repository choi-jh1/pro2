package com.ex.data;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportBoardFileDTO {
	private int fileId;
	private int reportId;
	private String originalName;
	private String savedName;
	private String mimeType;
	private long fileSize;
	private LocalDate uploadDate;
}




