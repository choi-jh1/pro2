package com.ex.repository;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper // SQL (쿼리문 : mapper.xml) 과 자바메서드를 연결하는 인터페이스  
public interface TestMapper {
	// 메서드명 = 쿼리문의 id 속성값과 일치해야함.
	public int memberCount();
}
