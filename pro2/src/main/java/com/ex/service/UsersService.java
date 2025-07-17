package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.UsersDTO;
import com.ex.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersMapper usersMapper;
	
	// 회원가입
	public void userInsert(UsersDTO dto) {
		dto.setRole((dto.getId()).equals("admin") ? "admin" : "user");
		usersMapper.userInsert(dto);
	}
	
	// 로그인 체크
	public UsersDTO loginCheck(UsersDTO dto) {
		return usersMapper.loginCheck(dto);
	}
	
	// 유저 목록
	public List<UsersDTO> userList() {
		return usersMapper.userList();
	}
		
}
