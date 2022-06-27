package com.assignment.collect.serviceImpl.similarity;

import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.similarity.SimilarityMapper;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.similarity.Similarity;
import com.assignment.collect.serviceimpl.similarity.SimilarityServiceImpl;
import com.assignment.collect.vo.ResultVo;
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
public class SimilarityServiceImplTest {

    @Mock
    DefectReportMapper defectReportMapper;

    @Mock
    SimilarityMapper similarityMapper;

    @InjectMocks
    SimilarityServiceImpl similarityService;

    DefectReport defectReport = new DefectReport();
    DefectReport defectReport1 = new DefectReport();
    DefectReport defectReport2 = new DefectReport();
    List<Similarity> similarities = new ArrayList<>();

    @BeforeEach
    public void set() {
        defectReport.setId(2L);
        defectReport.setTestRecordId(1L);
        defectReport.setOperatingSystem("WINDOWS");
        defectReport.setDeviceBrand("DELL");
        defectReport.setStatus("REJECTED");

        defectReport1.setId(3L);
        defectReport1.setTestRecordId(1L);
        defectReport1.setOperatingSystem("WINDOWS");
        defectReport1.setDeviceBrand("DELL");
        defectReport1.setStatus("PASSED");
        defectReport1.setDescription("哈哈哈test");

        defectReport2.setId(4L);
        defectReport2.setTestRecordId(1L);
        defectReport2.setOperatingSystem("WINDOWS");
        defectReport2.setDeviceBrand("DELL");
        defectReport2.setStatus("PASSED");
        defectReport2.setDescription("我是抄袭的");
        Similarity s1 = new Similarity();
        s1.setSimilarity(0.79);
        s1.setDefectReportId1(3L);
        s1.setDefectReportId2(4L);
        similarities.add(s1);
    }

    @Test
    void contextLoads() {
        System.out.println("PASSED");
    }


    @Test
    void testfindAuditDetailsfail() {
        when(defectReportMapper.selectByPrimaryKey(2L)).thenReturn(defectReport);
        ResultVo resultVo = similarityService.findAuditDetails(2, 10);
        Assertions.assertEquals(resultVo.getMsg(), "该报告未通过审核");
    }

    @Test
    void testAuditDetailssuccess() {
        when(defectReportMapper.selectByPrimaryKey(3L)).thenReturn(defectReport1);
        when(defectReportMapper.selectByPrimaryKey(4L)).thenReturn(defectReport2);
        when(similarityMapper.selectByReportId(3L)).thenReturn(similarities);//
        ResultVo resultVo = similarityService.findAuditDetails(3, 10);
        Assertions.assertEquals(resultVo.getMsg(), "推荐成功");

    }


}
