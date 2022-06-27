package com.assignment.collect.service.remark;

import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.remark.RemarkVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:49
 */
@Service
public interface RemarkService {

    ResultVo<RemarkVo> releaseRemark(RemarkVo remarkVo, Integer uid);

    PageInfo<RemarkVo> getRemarksByReportId(Integer currPage, Integer pageSize, Integer defectReportId, Integer uid);

    ResultVo<Double> getAvgRemarkByReportId(Integer uid, Integer reportid);
}
