package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService memberService;
	
	@GetMapping("main")
	public String main() {
		return "users/main";
	}
	
	@GetMapping("login")
	public String login() {
		return "users/login";
	}
	
	@GetMapping("insert")
	public String userInsert() {
		return "users/userInsert";
	}
}
