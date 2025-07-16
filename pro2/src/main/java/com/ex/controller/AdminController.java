package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@GetMapping("adminPage")
	public String admin(HttpSession session) {
		String role = (String)session.getAttribute("role");
		if(role==null || !role.equals("admin")) {
			return "redirect:/user/main";
		}
		return "admin/adminPage";
	}
	
	@GetMapping("reporterRegister")
	public String reporterRegister() {
		return "admin/reporterRegister";
	}
	
	@GetMapping("reportUpdate")
	public String reportUpdate() {
		return "admin/reportUpdate";
	}
	
	@GetMapping("reportManagement")
	public String reportManagement() {
		return "admin/reportManagement";
	} 
	
	@GetMapping("userUpdate")
	public String userUpdate(){
		return "admin/userUpdate";
	}
}
