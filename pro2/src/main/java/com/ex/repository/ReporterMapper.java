package com.ex.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.data.ReporterDTO;
import com.ex.data.UsersDTO;
@Mapper
@Repository
public interface ReporterMapper {
	ReporterDTO selectById(String id);
	
	// 기자 등록
	public void reporterInsert(ReporterDTO reporter);
	// 기자리스트(제보)
	public List<ReporterDTO> getReporterListWithStatus();
}
