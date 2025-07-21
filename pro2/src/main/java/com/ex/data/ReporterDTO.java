package com.ex.data;


import lombok.Data;

@Data
public class ReporterDTO {



	private String id;				// id
	private String writer_name;		// 기자 이름
	private String profile_img;		// 프로필 사진
	private String category;		// 기자 카테고리
	private String intro;			// 자기 소개
	private String name;
	private String email;
	private String assigned;
}

