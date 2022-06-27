package com.assignment.collect.vo.defectReport;

import com.assignment.collect.po.defectReport.DefectReport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: YuDongjun
 * @date: 2022/3/1 21:51
 * @description:
 */
@Data
public class DefectReportVo {
    private Long id;

    private Long testRecordId;

    private String operatingSystem;

    private String deviceBrand;

    private String status;

    private String screenshotPathList;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public DefectReportVo() {

    }

    public DefectReportVo(DefectReport defectReport) {
        this.id = defectReport.getId();
        this.testRecordId = defectReport.getTestRecordId();
        this.operatingSystem = defectReport.getOperatingSystem();
        this.deviceBrand = defectReport.getDeviceBrand();
        this.screenshotPathList = defectReport.getScreenshotPathList();
        this.description = defectReport.getDescription();
        this.createTime = defectReport.getCreateTime();
        this.status = defectReport.getStatus();
        this.title = defectReport.getTitle();
    }
}
