package com.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.data.MemberDTO;
import com.ex.repository.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 비지니스 로직 처리, Mapper - Controller
public class MemberService {
	private final MemberMapper memberMapper;
	
	// 회원가입
	public int memberInsert(MemberDTO memberDTO) {
		// 메서드 호출을 return 으로 보냄
		return memberMapper.memberInsert(memberDTO);
		
	}
	// 로그인
	public int loginCheck(String username, String password) {
		int result = memberMapper.loginCheck(username, password);
		// 변수에 담아서 return 시킴 
		return result; 
	}
	
	// 내 정보
	public MemberDTO myInfo(String username) {
		return memberMapper.myInfo(username);
	}
	
	// 전체 정보
	public List<MemberDTO> allInfo() {
		List<MemberDTO> list = memberMapper.allInfo();
		
		return list; 
	}
	// 회원탈퇴
	/*
	 * public int memberDelete(String username, String password) { return
	 * memberMapper.memberDelete(username, password); }
	 */
	public void delete(String username) {
		memberMapper.memberDelete(username);
	}
	// 회원정보 수정
	public void update(MemberDTO memberDTO) {
		memberMapper.memberUpdate(memberDTO);
	}
}




















