package com.assignment.collect.serviceImpl.remark;

import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.remark.RemarkMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.remark.Remark;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.serviceimpl.remark.RemarkAsync;
import com.assignment.collect.serviceimpl.remark.RemarkServiceImpl;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.remark.RemarkVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemarkServiceImplTests {
    @Mock
    RemarkMapper remarkMapper;

    @InjectMocks
    RemarkServiceImpl remarkService;
    @Mock
    DefectReportMapper defectReportMapper;
    @Mock
    TestRecordMapper testRecordMapper;
    @Mock
    UserCapabilityMapper userCapabilityMapper;
    @Mock
    UserMapper userMapper;
    @Mock
    RemarkAsync remarkAsync;


    Remark remark = new Remark();
    RemarkVo remarkVo = new RemarkVo();
    DefectReport defectReport=new DefectReport();
    User user=new User();
    List<Remark> lists = new ArrayList<>();
    UserCapability userCapability = new UserCapability();

    @BeforeEach
    public void set() {
        user.setCredit(10);

        remark.setUserId(1L);
        remark.setDescription("test");
        remark.setScore(90);
        remark.setDefectReportId(1L);
        remarkVo = new RemarkVo(remark);
        lists.add(remark);
    }

    @Test
    void releaseRemarkTest() {
        when(remarkMapper.insert(any(Remark.class))).thenReturn(1);
        when(defectReportMapper.selectByPrimaryKey(any())).thenReturn(defectReport);
        when(testRecordMapper.selectByPrimaryKey(any())).thenReturn(new TestRecord());
//        when(userCapabilityMapper.selectByPrimaryKey(any())).thenReturn(userCapability);
        when(remarkMapper.selectByPrimaryKey(any(),any())).thenReturn(null);
        when(userMapper.selectByPrimaryKey(any())).thenReturn(user);
        when(userMapper.updateByPrimaryKey(any())).thenReturn(1);
        ResultVo resultVo = remarkService.releaseRemark(remarkVo, 1);

        Assertions.assertEquals(resultVo.getMsg(), "报告评价发布成功");
    }

    @Test
    void getRemarksByReportIdTest() {
        when(remarkMapper.selectByReportId(1L)).thenReturn(lists);
        PageInfo<RemarkVo> pageInfo = remarkService.getRemarksByReportId(1, 1, 1, 1);
        Assertions.assertEquals(90, pageInfo.getList().get(0).getScore());
    }
}
