package com.assignment.collect.po.reportReview;

import com.assignment.collect.vo.reportReview.ReportReviewVo;

import java.io.Serializable;
import java.util.Date;

public class ReportReview implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long reportId;
    private Integer bugNums;
    private Double score;
    private Boolean isRepeatBug;
    private Boolean accepted;
    private Date createTime;

    public ReportReview() {

    }

    public ReportReview(ReportReviewVo reportReviewVo) {
        this.id = reportReviewVo.getId();
        this.reportId = reportReviewVo.getReportId();
        this.bugNums = reportReviewVo.getBugNums();
        this.score = reportReviewVo.getScore();
        this.isRepeatBug = reportReviewVo.getIsRepeatBug();
        this.accepted = reportReviewVo.getAccepted();
        this.createTime = reportReviewVo.getCreateTime();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getBugNums() {
        return bugNums;
    }

    public void setBugNums(Integer bugNums) {
        this.bugNums = bugNums;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getIsRepeatBug() {
        return isRepeatBug;
    }

    public void setIsRepeatBug(Boolean isRepeatBug) {
        this.isRepeatBug = isRepeatBug;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}