package com.assignment.collect.dao.defectReport;

import com.assignment.collect.po.defectReport.DefectReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface DefectReportMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(DefectReport record);

    int updateByPrimaryKey(DefectReport record);

    List<DefectReport> selectAll();

    List<DefectReport> selectByTestRecordId(Long testRecordId);


    int deleteByPrimaryKey(Long id);

    List<DefectReport> selectByTaskId(Long taskId);

    List<DefectReport> selectPassedByTaskId(Long taskId);

    DefectReport selectByPrimaryKey(Long id);
}
