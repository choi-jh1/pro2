package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pt/*")
public class PracticeController {
	
	@GetMapping("main")
	public String main() {
		return "main";
	}
	
	@GetMapping("login")
	public String login() {
		return "login";
	}
	@GetMapping("login1")
	public String logi1n() {
		return "login1";
	}
	@GetMapping("login2")
	public String logi2n() {
		return "login2";
	}
	@GetMapping("login2")
	public String login2() {
		return "login2";
	}
	
	@GetMapping("login33")
	public String login33() {
		return "login33";
	}
}
