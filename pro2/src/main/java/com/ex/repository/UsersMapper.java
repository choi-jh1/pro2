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
	// 아이디 중복확인
	public int idCheck(String id);
	// 아이디 찾기
	public String findId(UsersDTO dto);
	// 이메일,전화번호로 유저 확인
	public UsersDTO userCheck(UsersDTO dto);
	// 비밀번호 재설정
	public void pwUpdate(@Param("pw") String pw,@Param("id") String id);
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
	
	// 기자 정보 업데이트
	public UsersDTO findById(String id);
	public void update(UsersDTO user);
	
	// 회원 정보 수정
	public int userUpdate(UsersDTO dto);
	// 회원 정보
	public UsersDTO userInfo(String id);
	// 비밀번호 변경
	public void pwChange(@Param("pw1") String pw1,@Param("id") String id);
	// 비밀번호 확인
	public int pwCheck(UsersDTO dto);
}
