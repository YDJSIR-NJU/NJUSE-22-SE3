package com.assignment.collect.po.userCapability;

import java.io.Serializable;

public class UserCapability implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long uid;
    private Double collaboration;
    private Double censorshipAbility;
    private Double averageRemark;
    private Double reportRepeatability;
    private Double duplicateBugPer;
    private Long professionalAbility;
    private Long totalBugNums;

    public UserCapability() {
        this.collaboration = this.censorshipAbility = this.averageRemark = this.reportRepeatability = this.duplicateBugPer = 0.0;
        this.totalBugNums = this.professionalAbility = Long.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Double getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(Double collaboration) {
        this.collaboration = collaboration;
    }

    public Double getCensorshipAbility() {
        return censorshipAbility;
    }

    public void setCensorshipAbility(Double censorshipAbility) {
        this.censorshipAbility = censorshipAbility;
    }

    public Double getAverageRemark() {
        return averageRemark;
    }

    public void setAverageRemark(Double averageRemark) {
        this.averageRemark = averageRemark;
    }

    public Double getReportRepeatability() {
        return reportRepeatability;
    }

    public void setReportRepeatability(Double reportRepeatability) {
        this.reportRepeatability = reportRepeatability;
    }

    public Double getDuplicateBugPer() {
        return duplicateBugPer;
    }

    public void setDuplicateBugPer(Double duplicateBugPer) {
        this.duplicateBugPer = duplicateBugPer;
    }

    public Long getProfessionalAbility() {
        return professionalAbility;
    }

    public void setProfessionalAbility(Long professionalAbility) {
        this.professionalAbility = professionalAbility;
    }

    public Long getTotalBugNums() {
        return totalBugNums;
    }

    public void setTotalBugNums(Long totalBugNums) {
        this.totalBugNums = totalBugNums;
    }
}