package com.assignment.collect.vo.similarity;

import com.assignment.collect.po.similarity.Similarity;
import lombok.Data;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 13:55
 */
@Data
public class SimilarityVo {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long defectReportId1;
    private Long defectReportId2;
    private Double similarity;

    public SimilarityVo() {

    }

    public SimilarityVo(Similarity similarity) {
        this.id = similarity.getId();
        this.defectReportId1 = similarity.getDefectReportId1();
        this.defectReportId2 = similarity.getDefectReportId2();
        this.similarity = similarity.getSimilarity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDefectReportId1() {
        return defectReportId1;
    }

    public void setDefectReportId1(Long defectReportId1) {
        this.defectReportId1 = defectReportId1;
    }

    public Long getDefectReportId2() {
        return defectReportId2;
    }

    public void setDefectReportId2(Long defectReportId2) {
        this.defectReportId2 = defectReportId2;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

}
