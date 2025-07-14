package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.UsersDTO;

@Mapper
@Repository
public interface UsersMapper {
	// 회원가입
	public void userInsert(UsersDTO dto);
	// 로그인
	public int loginCheck(UsersDTO dto);
}
