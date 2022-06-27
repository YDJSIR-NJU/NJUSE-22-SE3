package com.assignment.collect.dao.reportDescriptor;

import com.assignment.collect.po.reportDescriptor.ReportDescriptor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportDescriptorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportDescriptor record);

    ReportDescriptor selectByPrimaryKey(Integer id);

    List<ReportDescriptor> selectAll();

    int updateByPrimaryKey(ReportDescriptor record);

    ReportDescriptor selectByReportId(Integer reportId);
}