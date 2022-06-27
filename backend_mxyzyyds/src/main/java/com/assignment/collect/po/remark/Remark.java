package com.assignment.collect.po.remark;

import com.assignment.collect.vo.remark.RemarkVo;

import java.io.Serializable;
import java.util.Date;

public class Remark implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long defectReportId;
    private Integer score;
    private Date createTime;
    private String description;

    public Remark() {

    }

    public Remark(RemarkVo remarkVO) {
        this.userId = remarkVO.getUserId();
        this.defectReportId = remarkVO.getdefectReportId();
        this.score = remarkVO.getScore();
        this.createTime = remarkVO.getCreateTime();
        this.description = remarkVO.getDescription();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDefectReportId() {
        return defectReportId;
    }

    public void setDefectReportId(Long defectReportId) {
        this.defectReportId = defectReportId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
