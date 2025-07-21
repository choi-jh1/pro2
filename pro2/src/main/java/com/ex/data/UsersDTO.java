package com.ex.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class UsersDTO {
	private String id;
	private String pw;
	private String nick;
	private String name;
	private LocalDate birth;
	private String phone;
	private String email;
	private String status;
	private String role;
	private LocalDateTime reg;
	private String category;
	private String assignedEmail;
}
