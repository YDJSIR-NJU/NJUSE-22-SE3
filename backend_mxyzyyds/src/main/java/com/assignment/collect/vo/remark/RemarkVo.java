package com.assignment.collect.vo.remark;

import com.assignment.collect.po.remark.Remark;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 13:53
 */
@Data
public class RemarkVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long defectReportId;
    private Integer score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String description;

    public RemarkVo() {

    }

    public RemarkVo(Remark remark) {
        this.defectReportId = remark.getDefectReportId();
        this.userId = remark.getUserId();
        this.score = remark.getScore();
        this.description = remark.getDescription();
        this.createTime = remark.getCreateTime();
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public Long getdefectReportId() {
        return defectReportId;
    }

    public void setdefectReportId(Long defectReportId) {
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
