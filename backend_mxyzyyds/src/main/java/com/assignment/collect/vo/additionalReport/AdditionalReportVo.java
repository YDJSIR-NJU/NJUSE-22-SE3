package com.assignment.collect.vo.additionalReport;

import com.assignment.collect.po.additionalReport.AdditionalReport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 12:03
 */
@Data
public class AdditionalReportVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long defectReportId;

    private Long testRecordId;

    private String operatingSystem;

    private String deviceBrand;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String screenshotPathList;

    private String description;

    private Long userId;

    public AdditionalReportVo() {

    }

    public AdditionalReportVo(AdditionalReport additionalReport) {
        this.id = additionalReport.getId();
        this.defectReportId = additionalReport.getDefectReportId();
        this.testRecordId = additionalReport.getTestRecordId();
        this.operatingSystem = additionalReport.getOperatingSystem();
        this.deviceBrand = additionalReport.getDeviceBrand();
        this.createTime = additionalReport.getCreateTime();
        this.screenshotPathList = additionalReport.getScreenshotPathList();
        this.description = additionalReport.getDescription();
        this.userId = additionalReport.getUserId();
    }

    public AdditionalReportVo(Long id, Long defectReportId, Long testRecordId, String operatingSystem, String deviceBrand, Date createTime, String screenshotPathList, String description, Long userId) {
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
