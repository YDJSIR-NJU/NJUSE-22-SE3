package com.assignment.collect.dao.reportReview;

import com.assignment.collect.po.reportReview.ReportReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ReportReviewMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(ReportReview record);

    ReportReview selectByPrimaryKey(Long id);

    List<ReportReview> selectAll();

    int updateByPrimaryKey(ReportReview record);

    List<ReportReview> selectByReportId(Long reportid);
}