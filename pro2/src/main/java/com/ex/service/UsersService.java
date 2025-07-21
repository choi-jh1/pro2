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
	
	// 아이디 중복확인
	public int idCheck(String id) {
		return usersMapper.idCheck(id);
	}
	
	// 로그인 체크
	public UsersDTO loginCheck(UsersDTO dto) {
		return usersMapper.loginCheck(dto);
	}
<<<<<<< HEAD
	
	public int userDelete(String id, String pw) {
		return usersMapper.userDelete(id, pw);
=======
	// 아이디,전화번호로 유저 확인
	public UsersDTO userCheck(UsersDTO dto) {
		return usersMapper.userCheck(dto);
	}
	// 아이디 찾기
	public String findId(UsersDTO dto) {
		return usersMapper.findId(dto);
	}
	// 비밀번호 재설정
	public void pwUpdate(String pw,String id) {
		usersMapper.pwUpdate(pw,id);
>>>>>>> cjh
	}
	// 유저 목록
	public List<UsersDTO> userList() {
		return usersMapper.userList();
	}
	
	// 유저 상태 변경
	public int updateStatus(String userId, String status) {
		return usersMapper.updateStatus(userId, status);
	}
	
	// 전체 기자 조회
	public List<UsersDTO> reporterList(){
		return usersMapper.reporterList();
	}
	
	// 기자 카테고리 변경
	public int updateCategory(String id, String category) {
		return usersMapper.updateCategory(id, category);
	}
	
	public UsersDTO getUserById(String id) {
		return usersMapper.findById(id);
	}
	
	public void updateUser(UsersDTO user) {
		usersMapper.update(user);
	}
		
}
