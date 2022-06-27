package com.assignment.collect.serviceimpl.activeness;

import com.assignment.collect.dao.activeness.ActivenessMapper;
import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.reportReview.ReportReviewMapper;
import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.enums.UserRole;
import com.assignment.collect.po.activeness.Activeness;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.reportReview.ReportReview;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ActivenessCalAsync {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ReportReviewMapper reportReviewMapper;
    @Autowired
    TestRecordMapper testRecordMapper;
    @Autowired
    DefectReportMapper defectReportMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ActivenessMapper activenessMapper;

    /**
     * TODO 异步更新所有用户活跃度或者能力值
     */
    @Async
    public void updateAllWk(Integer Time) {
        List<User> users = userMapper.selectAll();
        users.removeIf(user -> !(user.getUserRole().equals(UserRole.WORKER)));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Time);
        for (User user : users) {
            int reportNumsWk = 0, bugNumsWk = 0, taskNumsWk = 0, nonRepeatBugsNumsWk = 0;
            //对于每个用户
            List<TestRecord> testRecordListByUid = testRecordMapper.selectByUserId(user.getId());//测试记录
            List<DefectReport> defectReportListByUid = new ArrayList<>();//一个记录对应一个测试报告
            List<ReportReview> reportReviewListByUid = new ArrayList<>();//一个测试报告对应一个审核评价-最近一周bug/report
            List<Task> taskListByUid = new ArrayList<>();//根据测试记录获取用户任务
            /**
             * TODO 这里的removeif动态运行有问题
             */
            if (testRecordListByUid.size() > 0) {
                for (TestRecord tr : testRecordListByUid) {//根据记录找报告
                    List<DefectReport> dfl = defectReportMapper.selectByTestRecordId(tr.getId());
                    if (dfl.size() > 0) {
                        for (DefectReport df : dfl) {
                            defectReportListByUid.add(df);
                            List<ReportReview> rrl = reportReviewMapper.selectByReportId(df.getId());
                            if (rrl.size() > 0) reportReviewListByUid.add(rrl.get(0));
                        }
                        //Format:"DEC 17 23:16:43 CST 2022"

                    }
                }
                if (!testRecordListByUid.isEmpty())
                    testRecordListByUid.removeIf(testRecord -> testRecord.getCommitTime().before(cal.getTime()));
                if (!testRecordListByUid.isEmpty())
                    for (TestRecord tr : testRecordListByUid) {
                        Task task = taskMapper.selectByPrimaryKey(tr.getTaskId());
                        boolean needAdd = true;
                        for (Task tk : taskListByUid) {
                            if (tk.getId() == task.getId()) needAdd = false;
                        }
                        if (needAdd) {
                            taskListByUid.add(task);
                        }

                    }
                taskNumsWk = taskListByUid.size();
                if (reportReviewListByUid.size() > 0)
                    reportReviewListByUid.removeIf(reportReview -> reportReview.getCreateTime().before(cal.getTime()));//最近一周bug/report
                if (reportReviewListByUid.size() > 0) for (ReportReview rr : reportReviewListByUid) {
                    if (rr.getAccepted()) reportNumsWk++;
                    if (rr.getBugNums() == 1) bugNumsWk++;
                    if (!rr.getIsRepeatBug()) nonRepeatBugsNumsWk++;
                }

            } else {
                /**
                 * TODO 用户没有可用数据，不需要处理
                 */
            }

            Activeness activeness = activenessMapper.selectByUserID(user.getId());
            if (activeness == null) {
                activeness = new Activeness();
                activeness.setUid(user.getId());
                activenessMapper.insert(activeness);
                activeness = activenessMapper.selectByUserID(user.getId());
            }
            activeness.setNumbugsWeek(Long.valueOf(bugNumsWk));
            activeness.setNumreportsWeek(Long.valueOf(reportNumsWk));
            activeness.setNumtasksWeek(Long.valueOf(taskNumsWk));
            activenessMapper.updateByPrimaryKey(activeness);
        }

    }

    @Async
    public void updateAllMon(Integer Time) {
        List<User> users = userMapper.selectAll();
        users.removeIf(user -> !(user.getUserRole().equals(UserRole.WORKER)));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Time);
        for (User user : users) {
            int reportNumsMon = 0, bugNumsMon = 0, taskNumsMon = 0, nonRepeatBugsNumsMon = 0;
            //对于每个用户
            List<TestRecord> testRecordListByUid = testRecordMapper.selectByUserId(user.getId());//测试记录
            List<DefectReport> defectReportListByUid = new ArrayList<>();//一个记录对应一个测试报告
            List<ReportReview> reportReviewListByUid = new ArrayList<>();//一个测试报告对应一个审核评价-最近一周bug/report
            List<Task> taskListByUid = new ArrayList<>();//根据测试记录获取用户任务
            /**
             * TODO 这里的removeif动态运行有问题
             */
            if (testRecordListByUid.size() > 0) {
                for (TestRecord tr : testRecordListByUid) {//根据记录找报告
                    List<DefectReport> dfl = defectReportMapper.selectByTestRecordId(tr.getId());
                    if (dfl.size() > 0) {
                        for (DefectReport df : dfl) {
                            defectReportListByUid.add(df);
                            List<ReportReview> rrl = reportReviewMapper.selectByReportId(df.getId());
                            if (rrl.size() > 0) reportReviewListByUid.add(rrl.get(0));
                        }

                    }
                }
                if (!testRecordListByUid.isEmpty())
                    testRecordListByUid.removeIf(testRecord -> testRecord.getCommitTime().before(cal.getTime()));
                if (!testRecordListByUid.isEmpty())
                    for (TestRecord tr : testRecordListByUid) {
                        Task task = taskMapper.selectByPrimaryKey(tr.getTaskId());
                        boolean needAdd = true;
                        for (Task tk : taskListByUid) {
                            if (tk.getId() == task.getId()) needAdd = false;
                        }
                        if (needAdd) {
                            taskListByUid.add(task);
                        }

                    }
                taskNumsMon = taskListByUid.size();
                if (reportReviewListByUid.size() > 0)
                    reportReviewListByUid.removeIf(reportReview -> reportReview.getCreateTime().before(cal.getTime()));//最近一周bug/report
                if (reportReviewListByUid.size() > 0) for (ReportReview rr : reportReviewListByUid) {
                    if (rr.getAccepted()) reportNumsMon++;
                    if (rr.getBugNums() == 1) bugNumsMon++;
                    if (!rr.getIsRepeatBug()) nonRepeatBugsNumsMon++;
                }

            } else {
                /**
                 * TODO 用户没有可用数据，不需要处理
                 */
            }

            Activeness activeness = activenessMapper.selectByUserID(user.getId());
            if (activeness == null) {
                activeness = new Activeness();
                activeness.setUid(user.getId());
                activenessMapper.insert(activeness);
                activeness = activenessMapper.selectByUserID(user.getId());
            }
            activeness.setNumbugsMonth(Long.valueOf(bugNumsMon));
            activeness.setNumreportsMonth(Long.valueOf(reportNumsMon));
            activeness.setNumtasksMonth(Long.valueOf(taskNumsMon));
            activenessMapper.updateByPrimaryKey(activeness);
        }


    }
}
