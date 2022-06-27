package com.assignment.collect.po.defectReport;

import com.assignment.collect.vo.defectReport.DefectReportVo;

import java.io.Serializable;
import java.util.Date;

public class DefectReport implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long testRecordId;
    private String operatingSystem;
    private String deviceBrand;
    private Date createTime;
    private String status;
    private String screenshotPathList;
    private String title;
    private String description;

    public DefectReport(Long id, Long testRecordId, String screenshotPathList, String description,
                        String operatingSystem, String deviceBrand, Date createTime, String status, String title) {
        this.id = id;
        this.testRecordId = testRecordId;
        this.screenshotPathList = screenshotPathList;
        this.description = description;
        this.operatingSystem = operatingSystem;
        this.deviceBrand = deviceBrand;
        this.createTime = createTime;
        this.status = status;
        this.title = title;
    }

    public DefectReport(DefectReportVo defectReportVo) {
        this.id = defectReportVo.getId();
        this.testRecordId = defectReportVo.getTestRecordId();
        this.screenshotPathList = defectReportVo.getScreenshotPathList();
        this.description = defectReportVo.getDescription();
        this.operatingSystem = defectReportVo.getOperatingSystem();
        this.deviceBrand = defectReportVo.getDeviceBrand();
        this.createTime = defectReportVo.getCreateTime();
        this.status = defectReportVo.getStatus();
        this.title = defectReportVo.getTitle();
    }

    public DefectReport() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScreenshotPathList() {
        return screenshotPathList;
    }

    public void setScreenshotPathList(String screenshotPathList) {
        this.screenshotPathList = screenshotPathList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
