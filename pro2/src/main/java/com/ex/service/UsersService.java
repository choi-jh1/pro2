package com.ex.service;

import org.springframework.stereotype.Service;

import com.ex.data.UsersDTO;
import com.ex.repository.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersMapper usersMapper;
	
	public void userInsert(UsersDTO dto) {
		usersMapper.userInsert(dto);
	}
}
