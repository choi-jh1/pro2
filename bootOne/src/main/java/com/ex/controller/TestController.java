package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.repository.TestMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	private final TestMapper testMapper;
	
	@GetMapping("/test") // 실행주소 http://localhost:8080/test
	@ResponseBody
	public int test() {
		return testMapper.memberCount();
	}
	
}	
