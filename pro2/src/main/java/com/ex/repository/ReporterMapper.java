package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
@Mapper
@Repository
public interface ReporterMapper {
	// 기자 등록
	public void reporterInsert(ReporterDTO reporter);
	// 기자 목록
	public List<UsersDTO> reporterList();
	// 기자 정보
	public ReporterDTO reporterInfo(String id);
}
