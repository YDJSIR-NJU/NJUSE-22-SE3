package com.assignment.collect.vo.cluster;

import com.assignment.collect.vo.defectReport.DefectReportVo;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

@Data
public class ClusterVo {
    @NonNull
    Set<DefectReportVo> cluster;
    @NonNull
    Map<String, Float> wordcloud;

}
