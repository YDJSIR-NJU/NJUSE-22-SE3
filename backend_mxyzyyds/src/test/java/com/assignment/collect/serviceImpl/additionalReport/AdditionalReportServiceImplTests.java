package com.assignment.collect.serviceImpl.additionalReport;

import com.assignment.collect.dao.additionalReport.AdditionalReportMapper;
import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.po.additionalReport.AdditionalReport;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.serviceimpl.additionalReport.AdditionalReportServiceImpl;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.additionalReport.AdditionalReportVo;
import com.github.pagehelper.PageInfo;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdditionalReportServiceImplTests {
    @Mock
    AdditionalReportMapper additionalReportMapper;
    @Mock
    UserCapabilityMapper userCapabilityMapper;

    @Mock
    UserMapper userMapper;

    @Mock
    TestRecordMapper testRecordMapper;

    @Mock
    DefectReportMapper defectReportMapper;

    @InjectMocks
    AdditionalReportServiceImpl additionalReportService;

    AdditionalReport additionalReport = new AdditionalReport();
    List<AdditionalReport> lists = new ArrayList<>();
    AdditionalReportVo additionalReportVo = new AdditionalReportVo();
    User user = new User();

    @BeforeEach
    void set() {
        additionalReport.setId(1L);
        additionalReport.setDefectReportId(1L);
        lists.add(additionalReport);
        user.setCredit(10);
        user.setId(1L);
    }

    @Test
    void getDetailTest() {
        when(additionalReportMapper.selectByPrimaryKey(1L)).thenReturn(additionalReport);
        ResultVo<AdditionalReportVo> resultVo = additionalReportService.getDetail(1, 1);
        Assertions.assertEquals(resultVo.getMsg(), "补充报告获取成功");
    }

    @Test
    void getAdditionalReportsByReportIdTest() {
        when(additionalReportMapper.selectByReportId(1L)).thenReturn(lists);
        PageInfo<AdditionalReportVo> pageinfo = additionalReportService.getAdditionalReportsByReportId(1, 1, 1, 1);
        Assertions.assertEquals(pageinfo.getList().get(0).getId(), 1);
    }

    @Test
    void getAdditionalReportsByReportIdAndUidTest() {
        when(additionalReportMapper.selectByReportIdAndUserId(1L, 1L)).thenReturn(lists);
        PageInfo<AdditionalReportVo> pageInfo = additionalReportService.getAdditionalReportsByReportIdAndUid(1, 1, 1, 1);
        Assertions.assertEquals(pageInfo.getList().get(0).getId(), 1);

    }

    @Test
    void releaseAdditionalReportTest() throws IOException {
        when(additionalReportMapper.insert(any(AdditionalReport.class))).thenReturn(1);
        byte[] data1 = new byte[(int) (Math.random() * 1024 * 1024)];
        byte[] data2 = new byte[(int) (Math.random() * 1024 * 1024)];
        byte[] data3 = new byte[(int) (Math.random() * 1024 * 1024)];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = (byte) i;
        }

        for (int i = 0; i < data2.length; i++) {
            data2[i] = (byte) i;
        }

        for (int i = 0; i < data3.length; i++) {
            data3[i] = (byte) i;
        }
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(
                "file1.png",
                "file1.png",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data1));

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(
                "file2.bmp",
                "file2.bmp",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile(
                "file3.jpg",
                "file3.jpg",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data3));
        MultipartFile[] files = {mockMultipartFile1, mockMultipartFile2, mockMultipartFile3};
        when(userCapabilityMapper.selectByUserId(any())).thenReturn(new UserCapability());
        when(userMapper.selectByPrimaryKey(any())).thenReturn(user);
        ResultVo<AdditionalReportVo> resultVo = additionalReportService.releaseAdditionalReport(additionalReportVo, new MultipartFile[]{});
        Assertions.assertEquals(resultVo.getMsg(), "补充报告发布成功");
    }

}
