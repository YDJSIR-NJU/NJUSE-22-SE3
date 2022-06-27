package com.assignment.collect.po.additionalReport;

import com.assignment.collect.vo.additionalReport.AdditionalReportVo;

import java.io.Serializable;
import java.util.Date;

public class AdditionalReport implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long defectReportId;
    private Long testRecordId;
    private String operatingSystem;
    private String deviceBrand;
    private Date createTime;
    private String screenshotPathList;
    private String description;
    private Long userId;

    public AdditionalReport() {

    }

    public AdditionalReport(AdditionalReportVo additionalReportVO) {
        this.id = additionalReportVO.getId();
        this.defectReportId = additionalReportVO.getDefectReportId();
        this.testRecordId = additionalReportVO.getTestRecordId();
        this.operatingSystem = additionalReportVO.getOperatingSystem();
        this.deviceBrand = additionalReportVO.getDeviceBrand();
        this.createTime = additionalReportVO.getCreateTime();
        this.screenshotPathList = additionalReportVO.getScreenshotPathList();
        this.description = additionalReportVO.getDescription();
        this.userId = additionalReportVO.getUserId();
    }

    public AdditionalReport(Long id, Long defectReportId, Long testRecordId, String operatingSystem, String deviceBrand, Date createTime, String screenshotPathList, String description, Long userId) {
        this.id = id;
        this.defectReportId = defectReportId;
        this.testRecordId = testRecordId;
        this.operatingSystem = operatingSystem;
        this.deviceBrand = deviceBrand;
        this.createTime = createTime;
        this.screenshotPathList = screenshotPathList;
        this.description = description;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDefectReportId() {
        return defectReportId;
    }

    public void setDefectReportId(Long defectReportId) {
        this.defectReportId = defectReportId;
    }

    public Long getTestRecordId() {
        return testRecordId;
    }

    public void setTestRecordId(Long testRecordId) {
        this.testRecordId = testRecordId;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getScreenshotPathList() {
        return screenshotPathList;
    }

    public void setScreenshotPathList(String screenshotPathList) {
        this.screenshotPathList = screenshotPathList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
