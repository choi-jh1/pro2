package com.ex.data;

import java.time.LocalDateTime;

import lombok.Data;

/*
	create table board(
	 num number primary key,
	 writer varchar2(100) not null,
	 title varchar2(100) not null,
	 content varchar2(4000) not null,
	 pw varchar2(100) not null,
	 readCount number default 0,
	 ref number,
	 re_level number,
	 re_step number,
	 reg date default sysdate
	);
	
	create sequence board_seq nocache;
	commit;
*/
@Data
public class BoardDTO {
	private int num;
	private String writer;
	private String title;
	private String content;
	private String pw;
	private int readCount;
	private int ref;
	private int re_level;
	private int re_step;
	private LocalDateTime reg;
	

}




