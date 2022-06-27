package com.assignment.collect.serviceImpl.defectReport;

import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.remark.RemarkMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.remark.Remark;
import com.assignment.collect.serviceimpl.defectReport.DefectReportServiceImpl;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.defectReport.DefectReportVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DefectReportServiceImplTests {

    @Mock
    DefectReportMapper defectReportMapper;

    @Mock
    TestRecordMapper testRecordMapper;

    @Mock
    RemarkMapper remarkMapper;

    @InjectMocks
    DefectReportServiceImpl defectReportService;

    DefectReport defectReport;

    @BeforeEach
    public void set() {
        defectReport = new DefectReport();
        defectReport.setId(2L);
        defectReport.setTestRecordId(1L);
        defectReport.setOperatingSystem("WINDOWS");
        defectReport.setDeviceBrand("DELL");
    }

    @Test
    void contextLoads() {
        System.out.println("PASSED");
    }


    @Test
    public void getDetailSuccess() {
        DefectReport defectReport = new DefectReport();
        when(defectReportMapper.selectByPrimaryKey(2L)).thenReturn(defectReport);
        ResultVo<DefectReportVo> resultVo = defectReportService.getDetail(2, 2);
        Assertions.assertEquals(1, resultVo.getCode());
    }


    @Test
    public void getDetailFail() {
        DefectReport defectReport = new DefectReport();
        when(defectReportMapper.selectByPrimaryKey(2L)).thenReturn(null);
        ResultVo<DefectReportVo> resultVo = defectReportService.getDetail(2, 2);
        Assertions.assertEquals(0, resultVo.getCode());
    }

    @Test
    public void getReportsByTaskid() {
        int currPage = 1;
        int pageSize = 10;
        int uid = 1;
        int taskid = 1;

        List<DefectReport> reportList = new ArrayList<>();
        createReportList((int) (Math.random() * 100), reportList);
        when(defectReportMapper.selectByTaskId((long) taskid)).thenReturn(reportList);
        PageInfo<DefectReportVo> pageInfo = defectReportService.getReportsByTaskid(currPage, pageSize, uid, taskid);
        Assertions.assertEquals(reportList.size(), pageInfo.getList().size());
    }

    @Test
    public void getLowQualityReportsByTaskid() {
        int currPage = 1;
        int pageSize = 10;
        int uid = 1;
        int taskid = 1;
        Long defectReportId = Long.valueOf(1);
        List<DefectReport> reportList = new ArrayList<>();
        List<Remark> remarkList = new ArrayList<>();
        DefectReport defectReport = new DefectReport();
        defectReport.setId(Long.valueOf(1));
        reportList.add(defectReport);
        createRemarkList(10, remarkList);
//        when(defectReportMapper.selectByTaskId((long) taskid)).thenReturn(reportList);
//        when(remarkMapper.selectByReportId(defectReportId)).thenReturn(remarkList);
        PageInfo<DefectReportVo> pageInfo = defectReportService.getLowQualityReportsByTaskid(currPage, pageSize, taskid);
//        Assertions.assertEquals(reportList.size(), pageInfo.getList().size());

    }


    public void createReportList(int nums, List<DefectReport> list) {
        for (int i = 0; i < nums; i++) {
            list.add(new DefectReport());
        }
    }

    public void createRemarkList(int nums, List<Remark> list) {
        for (int i = 0; i < nums; i++) {
            Remark remark = new Remark();
            remark.setScore(1);
            list.add(remark);
        }
    }
}
