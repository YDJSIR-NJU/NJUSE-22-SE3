package com.assignment.collect.service.additionalReport;

import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.additionalReport.AdditionalReportVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:49
 */
@Service
public interface AdditionalReportService {
    ResultVo<AdditionalReportVo> getDetail(Integer uid, Integer additionalReportId);

    ResultVo<AdditionalReportVo> releaseAdditionalReport(AdditionalReportVo additionalReportVo, MultipartFile[] files);

    PageInfo<AdditionalReportVo> getAdditionalReportsByReportId(Integer currPage, Integer pageSize, Integer uid, Integer defectReportId);

    PageInfo<AdditionalReportVo> getAdditionalReportsByReportIdAndUid(Integer currPage, Integer pageSize, Integer uid, Integer defectReportId);

}
