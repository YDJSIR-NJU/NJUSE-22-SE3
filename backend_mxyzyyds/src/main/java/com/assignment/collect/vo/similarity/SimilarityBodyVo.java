package com.assignment.collect.vo.similarity;

import com.assignment.collect.vo.defectReport.DefectReportVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimilarityBodyVo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private DefectReportVo defectReport;
    private Double similarity;

}
