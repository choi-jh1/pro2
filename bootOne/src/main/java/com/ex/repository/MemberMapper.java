package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.data.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Repository
// DB 및 데이터 관련 작업을 하는 java 파일

@Mapper // 메서드를 정의 = xml (SQL 쿼리문 작성)
public interface MemberMapper {
	// 회원 가입
	public int memberInsert(MemberDTO member); // 여러개일때
	// 로그인 확인
	public int loginCheck(@Param("username") String username, @Param("password") String password); // 한두개일때
	// 해당 username에 대한 모든 정보
	public MemberDTO myInfo(String username);
	// member 전체 조회
	public List<MemberDTO> allInfo();
	/*
	 * // 회원탈퇴 public int memberDelete(@Param("username") String
	 * username, @Param("password") String password);
	 */
	
	// 회원탈퇴
	public void memberDelete(String username);
	
	// 내 정보 수정 -> 수정된 정보가 필요함 MemberDTO
	public void memberUpdate(MemberDTO memberDTO);
}
























