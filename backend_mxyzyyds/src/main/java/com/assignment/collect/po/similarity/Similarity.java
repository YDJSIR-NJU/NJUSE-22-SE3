package com.assignment.collect.po.similarity;

import com.assignment.collect.vo.similarity.SimilarityVo;

import java.io.Serializable;

public class Similarity implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long defectReportId1;
    private Long defectReportId2;
    private Double similarity;

    public Similarity() {

    }

    public Similarity(SimilarityVo similarityVO) {
        this.id = similarityVO.getId();
        this.defectReportId1 = similarityVO.getDefectReportId1();
        this.defectReportId2 = similarityVO.getDefectReportId2();
        this.similarity = similarityVO.getSimilarity();
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

    @Override
    public int compareTo(Object o) {
        Similarity similarity = (Similarity) o;
        if (Double.compare(this.similarity, similarity.similarity) == 1) return -1;
        else if (Double.compare(this.similarity, similarity.similarity) == -1) return 1;
        else return 0;
    }
}
