package com.assignment.collect.dao.remark;

import com.assignment.collect.po.remark.Remark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RemarkMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("defectReportId") Long defectReportId);

    int insert(Remark record);

    Remark selectByPrimaryKey(@Param("userId") Long userId, @Param("defectReportId") Long defectReportId);

    List<Remark> selectAll();

    int updateByPrimaryKey(Remark record);

    List<Remark> selectByReportId(Long defectReportId);

    List<Remark> selectByUserId(Long userId);
}
