package com.assignment.collect.serviceimpl.additionalReport;

import com.alibaba.fastjson.JSON;
import com.assignment.collect.dao.additionalReport.AdditionalReportMapper;
import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.po.additionalReport.AdditionalReport;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.service.additionalReport.AdditionalReportService;
import com.assignment.collect.util.COSUtil;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.FileHelper;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.additionalReport.AdditionalReportVo;
import com.assignment.collect.vo.file.FileInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.assignment.collect.util.Constant.ADDREPORT_SCORE;
import static com.assignment.collect.util.Constant.filePath;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:51
 */
@Service
public class AdditionalReportServiceImpl implements AdditionalReportService {

    @Autowired
    AdditionalReportMapper additionalReportMapper;

    @Autowired
    TestRecordMapper testRecordMapper;

    @Autowired
    DefectReportMapper defectReportMapper;

    @Autowired
    UserCapabilityMapper userCapabilityMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResultVo<AdditionalReportVo> releaseAdditionalReport(AdditionalReportVo additionalReportVo, MultipartFile[] files) {
        try {
            AdditionalReport additionalReport = new AdditionalReport(additionalReportVo);
            String curTimeMillis = String.valueOf(System.currentTimeMillis());
            List<FileInfoVo> fileInfoVoList = FileHelper.save(filePath + curTimeMillis + '/', files);
            List<String> screenShots = new ArrayList<>();
            for (FileInfoVo fileInfoVO : fileInfoVoList) {
                if (fileInfoVO.getType().matches(Constant.IMAGE_PATTERN)) {     //说明文件
                    String localPath = curTimeMillis + '/' + fileInfoVO.getPath();
                    screenShots.add(localPath);
                    COSUtil.uploadObject(filePath + localPath, "/upload/" + localPath);
                }
            }
            additionalReport.setScreenshotPathList(JSON.toJSONString(screenShots));   // 文件路径序列化
            additionalReport.setCreateTime(new Date(System.currentTimeMillis()));
            additionalReportMapper.insert(additionalReport);

            /**
             * TODO 参与协作任务证明参与了一项任务，需要更新用户报告协作度
             */
            List<TestRecord> testRecordList = testRecordMapper.selectByUserId(additionalReport.getUserId());
            List<AdditionalReport> additionalReportList = additionalReportMapper.selectByUserId(additionalReport.getUserId());
            testRecordList.removeIf(tR -> tR.getFinished().equals(false));
            int addSize = additionalReportList.size();
            int Tasksize = testRecordList.size();
            Double Collaboration = (addSize + 0.0) / Tasksize;
            UserCapability userCapability = userCapabilityMapper.selectByUserId(additionalReport.getUserId());
            if (userCapability == null) {
                userCapability = new UserCapability();
                userCapability.setUid(additionalReport.getUserId());
                userCapabilityMapper.insert(userCapability);
                userCapability = userCapabilityMapper.selectByUserId(additionalReport.getUserId());
            }
            userCapability.setCollaboration(Collaboration);
            userCapabilityMapper.updateByPrimaryKey(userCapability);
            /**
             * TODO 修改用户积分
             */
            User user = userMapper.selectByPrimaryKey(additionalReport.getUserId());
            if (user != null) user.setCredit(user.getCredit() + ADDREPORT_SCORE);
            userMapper.updateByPrimaryKey(user);
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "补充报告发布成功", new AdditionalReportVo(additionalReport));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return new ResultVo<>(Constant.REQUEST_FAIL, "IO错误，创建失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVo<>(Constant.REQUEST_FAIL, "补充报告创建失败");
        }
    }

    @Override
    public ResultVo<AdditionalReportVo> getDetail(Integer uid, Integer additionalReportId) {
        try {
            AdditionalReport additionalReport = additionalReportMapper.selectByPrimaryKey((long) additionalReportId);
            if (additionalReport == null) {
                return new ResultVo<>(Constant.REQUEST_FAIL, "该补充报告不存在");
            }
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "补充报告获取成功", new AdditionalReportVo(additionalReport));
        } catch (IndexOutOfBoundsException e) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "该补充报告不存在");
        }
    }

    @Override
    public PageInfo<AdditionalReportVo> getAdditionalReportsByReportId(Integer currPage, Integer pageSize, Integer uid, Integer defectReportId) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<AdditionalReport> po = new PageInfo<>(additionalReportMapper.selectByReportId((long) defectReportId));
        System.out.println(po);
        return getAdditionalReportVo(uid, po);
    }

    @Override
    public PageInfo<AdditionalReportVo> getAdditionalReportsByReportIdAndUid(Integer currPage, Integer pageSize, Integer uid, Integer defectReportId) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<AdditionalReport> po = new PageInfo<>(additionalReportMapper.selectByReportIdAndUserId((long) defectReportId, uid.longValue()));
        System.out.println(uid);
        return getAdditionalReportVo(uid, po);
    }

    PageInfo<AdditionalReportVo> getAdditionalReportVo(Integer uid, PageInfo<AdditionalReport> po) {
        return PageInfoUtil.convert(po, AdditionalReportVo.class);
    }
}
