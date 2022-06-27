package com.assignment.collect.dao.additionalReport;

import com.assignment.collect.po.additionalReport.AdditionalReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdditionalReportMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(AdditionalReport record);

    AdditionalReport selectByPrimaryKey(Long id);

    List<AdditionalReport> selectAll();

    int updateByPrimaryKey(AdditionalReport record);

    List<AdditionalReport> selectByReportId(Long defectReportId);

    List<AdditionalReport> selectByReportIdAndTestRecordId(@Param("defectReportId") Long defectReportId, @Param("testRecordId") Long testRecordId);

    List<AdditionalReport> selectByReportIdAndUserId(@Param("defectReportId") Long defectReportId, @Param("userId") Long userId);

    List<AdditionalReport> selectByUserId(Long userId);
}
