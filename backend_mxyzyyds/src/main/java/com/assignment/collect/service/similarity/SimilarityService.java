package com.assignment.collect.service.similarity;

import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.similarity.AuditDetailsVo;
import com.assignment.collect.vo.similarity.SimilarityConfigVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:50
 */
public interface SimilarityService {
    @Async
    void calSim(DefectReport defectReport);

    ResultVo<List<AuditDetailsVo>> findAuditDetails(Integer reportId, Integer topN);

    ResultVo<List<AuditDetailsVo>> recommend(Integer uid,
                                             Integer reportId,
                                             Integer topN);

    ResultVo<SimilarityConfigVo> getLimit(@RequestParam Integer uid);

    ResultVo<SimilarityConfigVo> setLimit(SimilarityConfigVo similarityConfigVo,
                                          Integer uid);
}
