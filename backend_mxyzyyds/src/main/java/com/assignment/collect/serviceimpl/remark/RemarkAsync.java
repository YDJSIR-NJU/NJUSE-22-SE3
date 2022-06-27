package com.assignment.collect.serviceimpl.remark;

import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.remark.RemarkMapper;
import com.assignment.collect.dao.reportReview.ReportReviewMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.remark.Remark;
import com.assignment.collect.po.reportReview.ReportReview;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.service.remark.RemarkService;
import com.assignment.collect.vo.remark.RemarkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkAsync {
    @Autowired
    private RemarkMapper remarkMapper;
    @Autowired
    private DefectReportMapper defectReportMapper;
    @Autowired
    private TestRecordMapper testRecordMapper;
    @Autowired
    private UserCapabilityMapper userCapabilityMapper;
    @Autowired
    private ReportReviewMapper reportReviewMapper;
    @Autowired
    private RemarkService remarkService;

    @Async
    public void updateRemarkAsync(RemarkVo remarkVo, Integer uid) {
        Remark remark = new Remark(remarkVo);
        DefectReport dr = defectReportMapper.selectByPrimaryKey(remark.getDefectReportId());
        TestRecord tr = testRecordMapper.selectByPrimaryKey(dr.getTestRecordId());
        UserCapability userCapability = userCapabilityMapper.selectByUserId(tr.getUserId());//别人的能力值受到影响
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(tr.getUserId());
            userCapabilityMapper.insert(userCapability);
            userCapability = userCapabilityMapper.selectByUserId(tr.getUserId());
        }
        Double remarks = 0.0;
        Long totalTimes = Long.valueOf(0);
        Double per = 0.0;
        List<TestRecord> testRecords = testRecordMapper.selectByUserId(tr.getUserId());
        if (testRecords.size() > 0) {
            for (TestRecord trd : testRecords) {
                List<DefectReport> drl = defectReportMapper.selectByTestRecordId(tr.getId());
                if (drl.size() > 0) {
                    for (DefectReport drd : drl) {
                        List<Remark> remarkList = remarkMapper.selectByReportId(drd.getId());
                        for (Remark rm : remarkList) {
                            remarks += rm.getScore();
                            totalTimes++;
                        }
                    }
                }
            }
            assert totalTimes > 0;
            per = remarks / totalTimes;
        }
        /**
         * TODO 影响别人评分（报告质量）
         */

        userCapability.setAverageRemark(per);
        userCapabilityMapper.updateByPrimaryKey(userCapability);

        Double variance = 0.0;//方差
        Integer size = 0;

        List<Remark> remarkList = remarkMapper.selectByUserId(remark.getUserId());//用户的所有打分记录
        if (remarkList != null && remarkList.size() > 0) {
            for (Remark remark1 : remarkList) {//用户所有的打分记录
                Double score1 = -10.0;
                List<ReportReview> reportReviewList = reportReviewMapper.selectByReportId(remark1.getDefectReportId());//找到被打分的报告的评审记录
                if (reportReviewList != null && reportReviewList.size() > 0) {
                    score1 = reportReviewList.get(0).getScore();//存在评审记录，发包方打分；
                    variance += (remark1.getScore() - score1) * (remark1.getScore() - score1);
                    size++;
                }
//                score2=remarkService.getAvgRemarkByReportId(uid, Math.toIntExact(remark1.getDefectReportId())).getData();//用户平均打分
//                if(Double.compare(score1,-10.0)==0)SCORE=score2;//说明发包方尚未打分，用户均分作为最终得分
//                else SCORE=(score1+score2)/2;//用户打分肯定存在，因为刚刚就是插进去了一条数据。
//                variance+=(remark1.getScore()-SCORE)*(remark1.getScore()-SCORE);
            }
            if (size == 0) {
                variance = -10.0;
            } else variance = Math.sqrt(variance / size);

        }
        /**
         * TODO 影响自己评分部分（审查能力）
         */
        userCapability = userCapabilityMapper.selectByUserId(remark.getUserId());
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(remark.getUserId());
            userCapabilityMapper.insert(userCapability);
            userCapability = userCapabilityMapper.selectByUserId(remark.getUserId());
        }
        userCapability.setCensorshipAbility(variance);//审查能力
        userCapabilityMapper.updateByPrimaryKey(userCapability);

    }

    @Async
    public void insertRemarkAsync(RemarkVo remarkVo, Integer uid) {
        Remark remark = new Remark(remarkVo);
        DefectReport dr = defectReportMapper.selectByPrimaryKey(remark.getDefectReportId());
        TestRecord tr = testRecordMapper.selectByPrimaryKey(dr.getTestRecordId());
        UserCapability userCapability = userCapabilityMapper.selectByUserId(tr.getUserId());//别人的能力值受到影响
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(tr.getUserId());
            userCapabilityMapper.insert(userCapability);
            userCapability = userCapabilityMapper.selectByUserId(tr.getUserId());
        }
        Double remarks = 0.0;
        Long totalTimes = Long.valueOf(0);
        Double per = 0.0;
        List<TestRecord> testRecords = testRecordMapper.selectByUserId(tr.getUserId());
        if (testRecords.size() > 0) {
            for (TestRecord trd : testRecords) {
                List<DefectReport> drl = defectReportMapper.selectByTestRecordId(tr.getId());
                if (drl.size() > 0) for (DefectReport drd : drl) {
                    List<Remark> remarkList = remarkMapper.selectByReportId(drd.getId());
                    for (Remark rm : remarkList) {
                        remarks += rm.getScore();
                        totalTimes++;
                    }
                }
            }
            assert totalTimes > 0;
            per = remarks / totalTimes;
        }
        /**
         * TODO 影响别人的评分部分（报告质量）
         */
        userCapability.setAverageRemark(per);
        userCapabilityMapper.updateByPrimaryKey(userCapability);

        Double variance = 0.0;//方差
        Integer size = 0;

        List<Remark> remarkList = remarkMapper.selectByUserId(remark.getUserId());//用户的所有打分记录
        if (remarkList != null && remarkList.size() > 0) {
            for (Remark remark1 : remarkList) {//用户所有的打分记录
                Double score1 = -10.0;
                List<ReportReview> reportReviewList = reportReviewMapper.selectByReportId(remark1.getDefectReportId());//找到被打分的报告的评审记录
                if (reportReviewList != null && reportReviewList.size() > 0) {
                    score1 = reportReviewList.get(0).getScore();//存在评审记录，发包方打分；
                    variance += (remark1.getScore() - score1) * (remark1.getScore() - score1);//和发包方打分的差距
                    size++;//总的数量
                }
//                score2=remarkService.getAvgRemarkByReportId(uid, Math.toIntExact(remark1.getDefectReportId())).getData();//用户平均打分
//                if(Double.compare(score1,-10.0)==0)SCORE=score2;//说明发包方尚未打分，用户均分作为最终得分
//                else SCORE=(score1+score2)/2;//用户打分肯定存在，因为刚刚就是插进去了一条数据。
//                variance+=(remark1.getScore()-SCORE)*(remark1.getScore()-SCORE);
            }
            if (size == 0) {
                variance = -10.0;
            } else variance = Math.sqrt(variance / size);

        }
        /**
         * TODO 影响自己评分部分（审查能力）
         */
        userCapability = userCapabilityMapper.selectByUserId(remark.getUserId());
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(remark.getUserId());
            userCapabilityMapper.insert(userCapability);
            userCapability = userCapabilityMapper.selectByUserId(remark.getUserId());
        }
        userCapability.setCensorshipAbility(variance);//审查能力
        userCapabilityMapper.updateByPrimaryKey(userCapability);

    }

}
