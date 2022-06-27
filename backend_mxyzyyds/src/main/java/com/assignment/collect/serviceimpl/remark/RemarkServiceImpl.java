package com.assignment.collect.serviceimpl.remark;

import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.remark.RemarkMapper;
import com.assignment.collect.dao.reportReview.ReportReviewMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.remark.Remark;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.service.remark.RemarkService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.remark.RemarkVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.assignment.collect.util.Constant.REMARK_SCORE;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:52
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private RemarkMapper remarkMapper;
    @Autowired
    private DefectReportMapper defectReportMapper;
    @Autowired
    private TestRecordMapper testRecordMapper;
    @Autowired
    private UserCapabilityMapper userCapabilityMapper;
    @Autowired
    private RemarkAsync remarkAsync;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo<RemarkVo> releaseRemark(RemarkVo remarkVo, Integer uid) {
        Remark remark = new Remark(remarkVo);
        remark.setCreateTime(new Date(System.currentTimeMillis()));
        DefectReport dr = defectReportMapper.selectByPrimaryKey(remark.getDefectReportId());
        TestRecord tr = testRecordMapper.selectByPrimaryKey(dr.getTestRecordId());
        UserCapability userCapability = userCapabilityMapper.selectByUserId(tr.getUserId());//别人的能力值受到影响
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(tr.getUserId());
            userCapabilityMapper.insert(userCapability);
        }

        if (remarkMapper.selectByPrimaryKey(remark.getUserId(), remark.getDefectReportId()) == null) {
            remarkMapper.insert(remark);
            /**
             * TODO 修改积分
             */
            User user = userMapper.selectByPrimaryKey(remark.getUserId());
            if (user != null) user.setCredit(user.getCredit() + REMARK_SCORE);
            userMapper.updateByPrimaryKey(user);
            /**
             * TODO 异步处理
             */
            remarkAsync.insertRemarkAsync(remarkVo, uid);
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "报告评价发布成功", new RemarkVo(remark));
        } else {
            remarkMapper.updateByPrimaryKey(remark);
            /**
             * TODO 异步处理
             */
            remarkAsync.updateRemarkAsync(remarkVo, uid);

            return new ResultVo<>(Constant.REQUEST_SUCCESS, "报告评价更新成功", new RemarkVo(remark));
        }

    }

    @Override
    public PageInfo<RemarkVo> getRemarksByReportId(Integer currPage, Integer pageSize, Integer defectReportId, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Remark> po = new PageInfo<>(remarkMapper.selectByReportId((long) defectReportId));
        return getRemarkVo(uid, po);
    }

    @Override
    public ResultVo<Double> getAvgRemarkByReportId(Integer uid, Integer reportid) {
        List<Remark> remarks = remarkMapper.selectByReportId((long) reportid);
        Integer size = remarks.size();
        Long totalRemarks = Long.valueOf(0);
        Double per = 0.0;
        if (size > 0) {
            for (Remark remark : remarks) {
                totalRemarks += remark.getScore();
            }
            per = (totalRemarks + 0.0) / size;
            return new ResultVo<Double>(Constant.REQUEST_SUCCESS, "平均值查询成功！", per);
        } else return new ResultVo<Double>(Constant.REQUEST_FAIL, "该报告尚未被评分！", per);
    }

    PageInfo<RemarkVo> getRemarkVo(Integer uid, PageInfo<Remark> po) {
        return PageInfoUtil.convert(po, RemarkVo.class);
    }
}
