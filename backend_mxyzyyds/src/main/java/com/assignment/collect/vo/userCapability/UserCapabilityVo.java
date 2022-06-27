package com.assignment.collect.vo.userCapability;

import com.assignment.collect.po.userCapability.UserCapability;

public class UserCapabilityVo {
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

    public UserCapabilityVo() {

    }

    public UserCapabilityVo(UserCapability userCapability) {
        this.id = userCapability.getId();
        this.uid = userCapability.getUid();
        this.collaboration = userCapability.getCollaboration();
        this.censorshipAbility = userCapability.getCollaboration();
        this.reportRepeatability = userCapability.getReportRepeatability();
        this.totalBugNums = userCapability.getTotalBugNums();
        this.averageRemark = userCapability.getAverageRemark();
        this.professionalAbility = userCapability.getProfessionalAbility();
        this.duplicateBugPer = userCapability.getDuplicateBugPer();
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