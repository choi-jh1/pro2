package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.UsersDTO;
import com.ex.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService usersService;
	
	@GetMapping("main")
	public String main() {
		return "user/main";
	}
	
	@GetMapping("insert")
	public String userInsert() {
		return "user/insertForm";
	}
	
	@PostMapping("insert")
	public String userInsert(UsersDTO dto) {
		usersService.userInsert(dto);
		return "redirect:/user/main";
	}
}
