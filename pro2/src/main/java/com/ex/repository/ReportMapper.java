package com.ex.repository;

import org.apache.ibatis.annotations.Mapper;
import com.ex.data.ReportBoardDTO;

@Mapper
public interface ReportMapper {
    void insertReport(ReportBoardDTO dto);
}
