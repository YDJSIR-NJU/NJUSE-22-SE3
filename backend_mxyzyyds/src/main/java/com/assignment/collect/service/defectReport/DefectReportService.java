package com.assignment.collect.service.defectReport;

import com.assignment.collect.dto.ReportReviewDTO.ReportReviewDTO;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.cluster.ClusterVo;
import com.assignment.collect.vo.defectReport.DefectReportVo;
import com.assignment.collect.vo.defectReport.DevVo;
import com.assignment.collect.vo.reportReview.ReportReviewVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: YuDongjun
 * @date: 2022/3/1 21:47
 * @description:
 */
@Service
public interface DefectReportService {
    ResultVo<DefectReportVo> getDetail(Integer uid, Integer defectReportId);

    ResultVo<DefectReportVo> releaseDefectReport(DefectReportVo defectReportVo, MultipartFile[] files);

    PageInfo<DefectReportVo> getReportsByTaskid(Integer currPage, Integer pageSize, Integer uid, Integer taskId);

    PageInfo<DefectReportVo> getReportsByTestRecordId(Integer currPage, Integer pageSize, Integer uid, Integer defectReportId);

    PageInfo<DefectReportVo> getLowQualityReportsByTaskid(Integer page, Integer pageSize, Integer taskId);

    ResultVo<DefectReportVo> reviewDefectReport(ReportReviewDTO reportReviewDTO);

    ResultVo<DevVo> getReportsDevByUserId(Integer uid);

    ResultVo<ReportReviewVo> getReviewByReportId(Integer reportId);

    ResultVo<List<ClusterVo>> getKMeans(Integer uid, Integer k, Integer keyword);

    ResultVo<List<ClusterVo>> getWorkerKMeans(Integer uid, Integer k, Integer keyword, Integer taskid);

    ResultVo<List<DefectReportVo>> getDistinctBugReport(Integer defectReportId);
}
