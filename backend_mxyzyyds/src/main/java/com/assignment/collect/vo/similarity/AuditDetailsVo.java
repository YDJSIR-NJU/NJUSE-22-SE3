package com.assignment.collect.vo.similarity;

import com.assignment.collect.vo.defectReport.DefectReportVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditDetailsVo {
    DefectReportVo defectReportVo;
    Double score;
}
