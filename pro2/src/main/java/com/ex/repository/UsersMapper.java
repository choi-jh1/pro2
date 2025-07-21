package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.UsersDTO;

@Mapper
@Repository
public interface UsersMapper {
	// 회원가입
	public void userInsert(UsersDTO dto);
	// 로그인
	public UsersDTO loginCheck(UsersDTO dto);
	// 회원탈퇴
	public int userDelete(@Param("id") String id, @Param("pw") String pw);
	// 유저목록
	public List<UsersDTO> userList();
	// 유저상태변경
	public int updateStatus(@Param("userId") String userId, @Param("status") String status);
	// 전체 기자 조회
	public List<UsersDTO> reporterList();
	// 기자 카테고리 변경
	public int updateCategory(@Param("id") String id, @Param("category") String category);
}
