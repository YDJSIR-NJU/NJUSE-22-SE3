package com.assignment.collect.vo.python;

import com.assignment.collect.po.defectReport.DefectReport;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimilarityCalcFormVO {
    private DefectReport defect_report;
    private DefectReport[] defect_reports;
}
