package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.UsersDTO;

@Mapper
@Repository
public interface UsersMapper {
	public void userInsert(UsersDTO dto);
}
