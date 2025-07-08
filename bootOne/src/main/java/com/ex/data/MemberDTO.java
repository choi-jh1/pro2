package com.ex.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/*
create table member(
	username varchar2(50) primary key,
	password varchar2(50) not null,
	birth 	 date 		  not null,
	email 	 varchar2(50) not null,
	reg 	 date		  default sysdate
);  
commit;
 */

@Data
public class MemberDTO {
	private String username;	// 사용자명	
	private String password;	// 비밀번호
	private LocalDate birth;	// 생년월일
	private String email;		// 이메일
	private LocalDateTime reg;  // 가입날짜
}
