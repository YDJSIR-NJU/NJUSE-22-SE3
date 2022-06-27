package com.assignment.collect.vo.reportReview;


import com.assignment.collect.po.reportReview.ReportReview;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ReportReviewVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long reportId;

    private Integer bugNums;

    private Double score;

    private Boolean isRepeatBug;

    private Boolean accepted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public ReportReviewVo() {

    }

    public ReportReviewVo(ReportReview reportReview) {
        this.id = reportReview.getId();
        this.reportId = reportReview.getReportId();
        this.bugNums = reportReview.getBugNums();
        this.score = reportReview.getScore();
        this.isRepeatBug = reportReview.getIsRepeatBug();
        this.accepted = reportReview.getAccepted();
        this.createTime = reportReview.getCreateTime();

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