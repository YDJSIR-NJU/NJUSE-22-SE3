package com.assignment.collect.serviceimpl.defectReport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.dao.activeness.ActivenessMapper;
import com.assignment.collect.dao.additionalReport.AdditionalReportMapper;
import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.dupBug.DupBugMapper;
import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.dao.remark.RemarkMapper;
import com.assignment.collect.dao.reportDescriptor.ReportDescriptorMapper;
import com.assignment.collect.dao.reportReview.ReportReviewMapper;
import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.dao.userDescriptor.UserDescriptorMapper;
import com.assignment.collect.dto.ReportReviewDTO.ReportReviewDTO;
import com.assignment.collect.po.activeness.Activeness;
import com.assignment.collect.po.additionalReport.AdditionalReport;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.dupBug.DupBug;
import com.assignment.collect.po.message.Message;
import com.assignment.collect.po.remark.Remark;
import com.assignment.collect.po.reportDescriptor.ReportDescriptor;
import com.assignment.collect.po.reportReview.ReportReview;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.po.userDescriptor.UserDescriptor;
import com.assignment.collect.prob.newWordCloud;
import com.assignment.collect.service.defectReport.DefectReportService;
import com.assignment.collect.service.remark.RemarkService;
import com.assignment.collect.service.similarity.SimilarityService;
import com.assignment.collect.util.*;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.cluster.ClusterVo;
import com.assignment.collect.vo.defectReport.DefectReportVo;
import com.assignment.collect.vo.defectReport.DevVo;
import com.assignment.collect.vo.file.FileInfoVo;
import com.assignment.collect.vo.reportReview.ReportReviewVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.assignment.collect.util.Constant.*;

/**
 * @author: YuDongjun
 * @date: 2022/3/1 21:50
 * @description:
 */
@Service

public class DefectReportServiceImpl implements DefectReportService {

    @Autowired
    DefectReportMapper defectReportMapper;

    @Autowired
    TestRecordMapper testRecordMapper;

    @Autowired
    SimilarityService similarityService;
    @Autowired
    RemarkService remarkService;
    @Autowired
    RemarkMapper remarkMapper;
    @Autowired
    ReportReviewMapper reportReviewMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserCapabilityMapper userCapabilityMapper;
    @Autowired
    ActivenessMapper activenessMapper;
    @Autowired
    AdditionalReportMapper additionalReportMapper;
    @Autowired
    ReportDescriptorMapper reportDescriptorMapper;
    @Autowired
    UserDescriptorMapper userDescriptorMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    DupBugMapper dupBugMapper;
    @Autowired
    TaskMapper taskMapper;


    @Override
    public ResultVo<DefectReportVo> getDetail(Integer uid, Integer defectReportId) {
        try {
            DefectReport defectReport = defectReportMapper.selectByPrimaryKey(Long.valueOf(defectReportId));
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "缺陷报告获取成功", new DefectReportVo(defectReport));
        } catch (Exception e) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "该缺陷报告不存在");
        }
    }

    @Override
    public ResultVo<DefectReportVo> releaseDefectReport(DefectReportVo defectReportVo, MultipartFile[] files) {
        try {
            DefectReport defectReport = new DefectReport(defectReportVo);
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
            defectReport.setScreenshotPathList(JSON.toJSONString(screenShots));   // 文件路径序列化
            defectReport.setCreateTime(new Date(System.currentTimeMillis()));

            defectReport.setStatus("PROCESSING");
            defectReportMapper.insert(defectReport);
            similarityService.calSim(defectReport);
            // 更新测试记录完成时间
            Long testRecordId = defectReport.getTestRecordId();
            TestRecord testRecord = testRecordMapper.selectByPrimaryKey(testRecordId);
            testRecordMapper.updateByPrimaryKey(testRecordId, testRecord.getTaskId(), testRecord.getUserId(), true, testRecord.getCommitTime(), new Date(System.currentTimeMillis()));
            /**
             * TODO 发布报告证明参与了一项任务，需要更新用户报告协作度
             */
            List<TestRecord> testRecordList = testRecordMapper.selectByUserId(testRecord.getUserId());
            List<AdditionalReport> additionalReportList = additionalReportMapper.selectByUserId(testRecord.getUserId());
            testRecordList.removeIf(tR -> tR.getFinished().equals(false));
            int addSize = additionalReportList.size();
            int Tasksize = testRecordList.size();
            Double Collaboration = (addSize + 0.0) / Tasksize;
            UserCapability userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());
            if (userCapability == null) {
                userCapability = new UserCapability();
                userCapability.setUid(testRecord.getUserId());
                userCapabilityMapper.insert(userCapability);
                userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());
            }
            userCapability.setCollaboration(Collaboration);
            userCapabilityMapper.updateByPrimaryKey(userCapability);
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "缺陷报告发布成功", new DefectReportVo(defectReport));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return new ResultVo<>(Constant.REQUEST_FAIL, "IO错误，创建失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVo<>(Constant.REQUEST_FAIL, "缺陷报告创建失败");
        }
    }

    @Override
    public PageInfo<DefectReportVo> getReportsByTaskid(Integer currPage, Integer pageSize, Integer uid, Integer taskid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<DefectReport> po = new PageInfo<>(defectReportMapper.selectByTaskId(Long.valueOf(taskid)));
        return getDefectReportVo(uid, po);
    }

    @Override
    public PageInfo<DefectReportVo> getReportsByTestRecordId(Integer currPage, Integer pageSize, Integer uid, Integer testRecordId) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<DefectReport> po = new PageInfo<>(defectReportMapper.selectByTestRecordId(Long.valueOf(testRecordId)));
        return getDefectReportVo(uid, po);
    }

    @Override
    public PageInfo<DefectReportVo> getLowQualityReportsByTaskid(Integer page, Integer pageSize, Integer taskId) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, pageSize);
        List<DefectReport> defectReportByTaskId = defectReportMapper.selectPassedByTaskId(Long.valueOf(taskId));
        List<DefectReport> result = new ArrayList<>();
        if (defectReportByTaskId.size() != 0){
            for (DefectReport d : defectReportByTaskId) {
                List<Remark> remarks = remarkMapper.selectByReportId(d.getId());
                Double avg = 0.0;
                int sum = 0;
                for (Remark remark : remarks) {
                    sum++;
                    avg += remark.getScore();
                }
                if (sum != 0 && !avg.isNaN()) {
                    avg = avg / sum;
                }
                if (avg <= 4 && avg > 0.0 && Objects.equals(d.getStatus(), "PASSED")) result.add(d);
            }
        }
        PageInfo<DefectReport> po = new PageInfo<>(result);
        return getDefectReportVo(0, po);
    }

    /**
     * 审核报告
     *
     * @param
     * @return
     */
    @Override
    public ResultVo<DefectReportVo> reviewDefectReport(ReportReviewDTO object) {

        ReportReviewVo reportReviewVo = new ReportReviewVo();
        reportReviewVo.setReportId(object.getReportId());
        reportReviewVo.setBugNums(object.getBugNums());
        reportReviewVo.setScore(object.getScore());
        reportReviewVo.setIsRepeatBug(object.getIsRepeatBug());
        reportReviewVo.setAccepted(object.getAccepted());

        /**
         * TODO 异常状况处理
         */
        int result = 0;
        ReportReview reportReview = new ReportReview(reportReviewVo);
        reportReview.setCreateTime(new Date());
        List<ReportReview> reportReview1 = reportReviewMapper.selectByReportId(reportReviewVo.getReportId());
        if (reportReview1 != null && reportReview1.size() != 0) {
            reportReview.setId(reportReview1.get(0).getId());
            result = reportReviewMapper.updateByPrimaryKey(reportReview);
        } else result = reportReviewMapper.insert(reportReview);
        if (result > 0) {
            DefectReport defectReport = defectReportMapper.selectByPrimaryKey(reportReview.getReportId());
            Long TestRecordId = defectReport.getTestRecordId();
            TestRecord testRecord = testRecordMapper.selectByPrimaryKey(TestRecordId);
            UserCapability userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());

            if (userCapability == null) {
                userCapability = new UserCapability();
                userCapability.setUid(testRecord.getUserId());
                userCapabilityMapper.insert(userCapability);
                userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());
            }
            Activeness activeness = activenessMapper.selectByUserID(testRecord.getUserId());
            if (activeness == null) {
                activeness = new Activeness();
                activeness.setUid(testRecord.getUserId());
                activenessMapper.insert(activeness);
                activeness = activenessMapper.selectByUserID(testRecord.getUserId());
            }
            if (reportReview.getIsRepeatBug()) {
                userCapability.setDuplicateBugPer((userCapability.getDuplicateBugPer() * userCapability.getTotalBugNums() + 1.0) / (userCapability.getTotalBugNums() + 1));
                Message message = new Message();
                message.setStatus("NEW");
                message.setContent("您提交的" + reportReview.getReportId() + "号报告发现的BUG与已有BUG重复，但仍感谢您的参与！");
                message.setTime(new Date());
                message.setUserId(userCapability.getUid());
                messageMapper.insert(message);

                if (object.getDupTag() != null) {       // 插入重复bug
                    DupBug dupBug = new DupBug();
                    dupBug.setReportid(object.getReportId().intValue());
                    dupBug.setDupid(object.getDupTag());
                    dupBugMapper.insert(dupBug);
                }

                //todo 触发检测是否需要提醒发包方任务差不多可以结束了
                remainderPublisher(reportReview);
            } else {
                /**
                 * TODO 发现了非重复bug,修改用户积分
                 */
                User user = userMapper.selectByPrimaryKey(testRecord.getUserId());
                if (user != null) user.setCredit(user.getCredit() + NEWBUG_SCORE);
                userMapper.updateByPrimaryKey(user);
                Message message = new Message();
                message.setStatus("NEW");
                message.setContent("恭喜，您提交的" + reportReview.getReportId() + "号报告发现了新的BUG！");
                message.setTime(new Date());
                message.setUserId(user.getId());
                messageMapper.insert(message);

                remainderPublisher(reportReview);
            }
            if (reportReview.getBugNums() == 1) {
                activeness.setLastbugtime(new Date());
                userCapability.setTotalBugNums(userCapability.getTotalBugNums() + 1);
            } else {
                Message message = new Message();
                message.setStatus("NEW");
                message.setContent("对不起，您提交的" + reportReview.getReportId() + "号报告没有发现BUG！");
                message.setTime(new Date());
                message.setUserId(activeness.getUid());
                messageMapper.insert(message);
            }

            activeness.setLastreporttime(new Date());
            /**
             * TODO计算用户各种能力
             */

            if (reportReview.getAccepted()) {
                /**
                 * TODO 修改report状态
                 */
                defectReport.setStatus("PASSED");
                activeness.setLastreporttime(new Date());
                defectReportMapper.updateByPrimaryKey(defectReport);
                /**
                 * TODO 修改用户积分
                 */
                User user = userMapper.selectByPrimaryKey(testRecord.getUserId());
                if (user != null) user.setCredit(user.getCredit() + ACCEPTED_REPORT_SCORE);
                userMapper.updateByPrimaryKey(user);
                Message message = new Message();
                message.setStatus("NEW");
                message.setContent("恭喜，您提交的" + reportReview.getReportId() + "号报告已被发包方接收！");
                message.setTime(new Date());
                message.setUserId(activeness.getUid());
                messageMapper.insert(message);

            } else {
                /**
                 * TODO 修改report状态
                 */
                defectReport.setStatus("REJECTED");
                defectReportMapper.updateByPrimaryKey(defectReport);
                /**
                 * TODO 报告审核不通过，积分减少
                 */
                User user = userMapper.selectByPrimaryKey(testRecord.getUserId());
                if (user != null) user.setCredit(user.getCredit() - REJECTED_REPORT_SCORE);
                userMapper.updateByPrimaryKey(user);
                Message message = new Message();
                message.setStatus("NEW");
                message.setContent("抱歉，您提交的" + reportReview.getReportId() + "号报告被发包方拒绝。下次再接再厉吧！！");
                message.setTime(new Date());
                message.setUserId(user.getId());
                messageMapper.insert(message);

                /**
                 * todo 报告审核不通过，删除关键词
                 */
                try {
                    // 取出该报告对应的报告描述词
                    ReportDescriptor reportDescriptor = reportDescriptorMapper.selectByReportId(defectReport.getId().intValue());
                    if (reportDescriptor != null) {
                        List<String> list = JSONObject.parseObject(reportDescriptor.getWords(), List.class);
                        UserDescriptor userDescriptor = userDescriptorMapper.selectByUid(testRecord.getUserId().intValue());
                        HashMap<String, Integer> descriptorMap = JSONObject.parseObject(userDescriptor.getWords(), HashMap.class);
                        // 更新用户描述性词
                        for (String word : list) {
                            if (descriptorMap.containsKey(word) && descriptorMap.get(word) > 0) {
                                descriptorMap.put(word, descriptorMap.get(word) - 1);
                            }
                        }
                        userDescriptorMapper.updateByPrimaryKey(userDescriptor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /**
             * 更新报告重复度
             */
            List<TestRecord> testRecords = testRecordMapper.selectByUserId(testRecord.getUserId());
            long duprep = 0;
            long sum = Long.valueOf(0);
            Double per = 0.0;
            if (testRecords.size() > 0) for (TestRecord tr : testRecords) {
                List<DefectReport> drl = defectReportMapper.selectByTestRecordId(tr.getId());
                if (drl.size() > 0) for (DefectReport dr : drl) {
                    sum++;
                    if (dr.getStatus().equals("REJECTED")) duprep++;
                }
                per = (duprep + 0.0) / sum;
            }
            userCapability.setReportRepeatability(per);
            /**
             * TODO 根据bugnums和score和is_repeat_bug更新用户属性
             */
            userCapabilityMapper.updateByPrimaryKey(userCapability);
            activenessMapper.updateByPrimaryKey(activeness);
            return new ResultVo<DefectReportVo>(Constant.REQUEST_SUCCESS, "审核状态更新成功",
                    new DefectReportVo(defectReport));
        }
        return new ResultVo<DefectReportVo>(Constant.REQUEST_FAIL, "数据库插入失败");
    }

    @Override
    public ResultVo<DevVo> getReportsDevByUserId(Integer uid) {
        List<TestRecord> testRecordList = testRecordMapper.selectByUserId(uid.longValue());
        List<DefectReport> defectReportList = new ArrayList<>();
        Long WINDOWS = Long.valueOf(0),
                LINUX = Long.valueOf(0),
                MACOS = Long.valueOf(0),
                ANDROID = Long.valueOf(0),
                IOS = Long.valueOf(0),
                IPADOS = Long.valueOf(0),
                OTHER = Long.valueOf(0);
        if (testRecordList != null && testRecordList.size() != 0) {
            for (TestRecord testRecord : testRecordList) {
                List<DefectReport> drList = defectReportMapper.selectByTestRecordId(testRecord.getId());
                if (drList != null && drList.size() > 0) {
                    for (DefectReport dr : drList) {
                        if (dr.getOperatingSystem().equals("WINDOWS")) WINDOWS++;
                        else if (dr.getOperatingSystem().equals("LINUX")) LINUX++;
                        else if (dr.getOperatingSystem().equals("MACOS")) MACOS++;
                        else if (dr.getOperatingSystem().equals("ANDROID")) ANDROID++;
                        else if (dr.getOperatingSystem().equals("IOS")) IOS++;
                        else if (dr.getOperatingSystem().equals("IPADOS")) IPADOS++;
                        else if (dr.getOperatingSystem().equals("OTHER")) OTHER++;
                    }
                }
            }
            DevVo devVo = new DevVo(WINDOWS, LINUX, MACOS, ANDROID, IOS, IPADOS, OTHER);
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "获取用户设备数成功！", devVo);
        } else {
            DevVo devVo = new DevVo(WINDOWS, LINUX, MACOS, ANDROID, IOS, IPADOS, OTHER);
            return new ResultVo<>(Constant.REQUEST_FAIL, "用户没有参与过测试！", devVo);
        }


    }

    @Override
    public ResultVo<ReportReviewVo> getReviewByReportId(Integer reportId) {
        List<ReportReview> reportReviewList = reportReviewMapper.selectByReportId(Long.valueOf(reportId));
        if (reportReviewList != null && reportReviewList.size() > 0) {
            return new ResultVo<ReportReviewVo>(REQUEST_SUCCESS, "查询审核状态成功！", new ReportReviewVo(reportReviewList.get(0)));
        }
        return new ResultVo<ReportReviewVo>(REQUEST_FAIL, "该报告尚未被评审！");
    }

    @Override
    public ResultVo<List<ClusterVo>> getKMeans(Integer uid, Integer k, Integer keyword) {
        if (k < 0) k = (-1) * k;
        Integer preK = k;
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();
        List<TestRecord> testRecordList = testRecordMapper.selectByUserId(Long.valueOf(uid));
        List<DefectReport> defectReportLists = new ArrayList<>();//用户发布的报告列表
        HashMap<Long, List<String>> fenciresult = new HashMap<>();
        if (testRecordList != null && testRecordList.size() > 0) {
            //说明用户参加了测试任务
            for (TestRecord testRecord : testRecordList) {
                List<DefectReport> defectReportList = defectReportMapper.selectByTestRecordId(testRecord.getId());
                if (defectReportList != null && defectReportList.size() > 0) {
                    defectReportLists.addAll(defectReportList);
                }
            }
            defectReportLists.removeIf(defectReport -> defectReport.getStatus().equals("REJECTED"));
            if (defectReportLists.size() > 0) {
                /**
                 * TODO 对用户报告进行聚类,是否需要将被拒绝的报告进行聚类？
                 */
                for (DefectReport defectReport : defectReportLists) {

                    List<String> desc = new ArrayList<>();
//                    desc.add(defectReport.getOperatingSystem());
//                    desc.add(defectReport.getDeviceBrand());
                    List<Term> title = StandardTokenizer.segment(defectReport.getTitle());//进行分词
                    for (Term term : title) {
                        desc.add(term.word);
                    }
                    List<Term> description = StandardTokenizer.segment(defectReport.getDescription());//进行分词
                    for (Term term : description) {
                        desc.add(term.word);
                    }
                    fenciresult.put(defectReport.getId(), desc);//存放每个报告分词结果
                    analyzer.addDocument(String.valueOf(defectReport.getId()), desc);
                }

                List<Set<String>> lists = new ArrayList<>();
//                if(defectReportLists.size()<k)k=defectReportLists.size();
                if (defectReportLists.size() == 0) return new ResultVo<>(REQUEST_FAIL, "用户尚未发布有效报告！");
                else if (defectReportLists.size() <= k) {
                    k = defectReportLists.size();
                    lists = analyzer.kmeans(k);//list.size()==k
//                    lists=analyzer.repeatedBisection(1.0);
                } else if (defectReportLists.size() > k && k > 0) {
                    lists = analyzer.kmeans(k);//list.size()==k
                } else lists = analyzer.repeatedBisection(1.0);
                List<ClusterVo> results = new ArrayList<>();
                for (Set set : lists) {
                    //进入某个类
                    Set<DefectReportVo> defectReportVos = new HashSet<>();
                    List<String> groupfenci = new ArrayList<>();
                    String[] descriptions = new String[0];
                    List<String> groupkeywordList = new ArrayList<>();
                    Map<String, Float> worldCloud = new HashMap<>();
                    if (set.size() > 0) {
                        for (Object id : set) {
                            /**
                             * TODO 说明用户这里不再需要传报告了
                             */
//                            defectReportVos.add(new DefectReportVo(
//                                    defectReportMapper.selectByPrimaryKey(
//                                    Long.valueOf(String.valueOf(id)))));
                            groupfenci.addAll(fenciresult.get(Long.valueOf(String.valueOf(id))));
                        }
                        worldCloud = new newWordCloud().getTermAndRank(groupfenci.toString(), keyword);//带词频的关键词提取
//                        groupkeywordList = HanLP.extractKeyword(groupfenci.toString(), keyword);//关键词提取
//                        descriptions=new String[groupkeywordList.size()];
//                        int index=0;
//                        for (String id : groupkeywordList) {
//                            descriptions[index++]=id;
//                        }
                    }
                    ClusterVo clusterVo = new ClusterVo(defectReportVos, worldCloud);
                    results.add(clusterVo);
                }
                return new ResultVo<>(REQUEST_SUCCESS, "聚类成功", results);

            } else {
                return new ResultVo<>(REQUEST_FAIL, "用户尚未发布有效报告！");
            }

        } else {
            return new ResultVo<>(REQUEST_FAIL, "用户尚未参与测试任务!");
        }

    }

    /**
     * 获取工人任务下报告列表的聚类结果
     *
     * @param uid
     * @param k
     * @param keyword
     * @param taskid
     * @return
     */
    @Override
    public ResultVo<List<ClusterVo>> getWorkerKMeans(Integer uid, Integer k, Integer keyword, Integer taskid) {
        if (k < 0) k = (-1) * k;
        Integer preK = k;
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();
        List<TestRecord> testRecordList = testRecordMapper.selectByTaskId(Long.valueOf(taskid));//获取所有有关该任务的测试记录
        List<DefectReport> defectReportList = new ArrayList<>();//与该任务相关的所有报告的集合
        HashMap<Long, List<String>> fenciresult = new HashMap<>();
        if (testRecordList != null && testRecordList.size() > 0) {
            for (TestRecord tR : testRecordList) {
                //每个用户会有一个记录,遍历一遍获取所有用户报告
                List<DefectReport> defectReports = defectReportMapper.selectByTestRecordId(tR.getId());
                defectReportList.addAll(defectReports);
            }
            if (defectReportList.size() > 0)
                defectReportList.removeIf(defectReport -> defectReport.getStatus().equals("REJECTED"));
            /**
             * TODO 对任务下的所有报告进行处理,是否需要将被拒绝的报告计算在内？
             */

            for (DefectReport defectReport : defectReportList) {

                List<String> desc = new ArrayList<>();
                desc.add(defectReport.getOperatingSystem());
                desc.add(defectReport.getDeviceBrand());
                List<Term> title = StandardTokenizer.segment(defectReport.getTitle());//进行分词
                for (Term term : title) {
                    desc.add(term.word);
                }
                List<Term> description = StandardTokenizer.segment(defectReport.getDescription());//进行分词
                for (Term term : description) {
                    desc.add(term.word);
                }
                fenciresult.put(defectReport.getId(), desc);//存放每个报告分词结果
                analyzer.addDocument(String.valueOf(defectReport.getId()), desc);
            }
            List<Set<String>> lists = new ArrayList<>();
//                if(defectReportLists.size()<k)k=defectReportLists.size();
            if (defectReportList.size() == 0) return new ResultVo<>(REQUEST_FAIL, "该任务尚未收到有效报告！");
            else if (defectReportList.size() <= k) {
                k = defectReportList.size();
                lists = analyzer.kmeans(k);//list.size()==k
//                    lists=analyzer.repeatedBisection(1.0);
            } else if (defectReportList.size() > k && k > 0) {
                lists = analyzer.kmeans(k);//list.size()==k
            } else lists = analyzer.repeatedBisection(1.0);
            List<ClusterVo> results = new ArrayList<>();
            for (Set set : lists) {
                //进入某个类
                Set<DefectReportVo> defectReportVos = new HashSet<>();
                List<String> groupfenci = new ArrayList<>();
                String[] descriptions = new String[0];
                List<String> groupkeywordList = new ArrayList<>();
                Map<String, Float> worldCloud = new HashMap<>();
                if (set.size() > 0) {
                    for (Object id : set) {
                        defectReportVos.add(new DefectReportVo(
                                defectReportMapper.selectByPrimaryKey(
                                        Long.valueOf(String.valueOf(id)))));
                        groupfenci.addAll(fenciresult.get(Long.valueOf(String.valueOf(id))));
                    }

                    worldCloud = new newWordCloud().getTermAndRank(groupfenci.toString(), keyword);//带词频的关键词提取
//
                    //     groupkeywordList = HanLP.extractKeyword(groupfenci.toString(), keyword);//关键词提取
//                    descriptions=new String[groupkeywordList.size()];
//                    int index=0;
//                    for (String id : groupkeywordList) {
//                        descriptions[index++]=id;
//                    }
                }
                ClusterVo clusterVo = new ClusterVo(defectReportVos, worldCloud);
                results.add(clusterVo);
            }
            return new ResultVo<>(REQUEST_SUCCESS, "聚类成功", results);
        } else {
            return new ResultVo<>(REQUEST_FAIL, "该任务尚未收到有效测试报告！");
        }


    }


    PageInfo<DefectReportVo> getDefectReportVo(Integer uid, PageInfo<DefectReport> po) {
        return PageInfoUtil.convert(po, DefectReportVo.class);
    }

    public void remainderPublisher(ReportReview reportReview) {
        // 获取captureProcess
        TreeMap<Integer, List<ReportReview>> captureProcess = getCaptureProcess(reportReview.getReportId().intValue());
        List<DupBug> dupBugList = new ArrayList<>();
        if (captureProcess != null) {
            dupBugList = getDupBugList(reportReview.getReportId().intValue());
        } else {
            return;
        }
        if (MhCHCRCAlgorithm.obtainRecaptureResults(captureProcess, dupBugList)) {       // 如果bug已经基本发现完毕的话
            DefectReport defectReport = defectReportMapper.selectByPrimaryKey(reportReview.getReportId()); // 获取评审记录对应的报告
            TestRecord testRecord = testRecordMapper.selectByPrimaryKey(defectReport.getTestRecordId());    // 获取报告对应的测试记录
            Long taskId = testRecord.getTaskId();   // 获取测试记录对应的任务的id
            Task task = taskMapper.selectByPrimaryKey(taskId);
            long uid = task.getUserId();
            Message message = new Message();
            message.setStatus("NEW");
            message.setContent("系统检测到您的第" + taskId + "号测试任务的bug已经基本寻找完，你可以选择在任务详情页提前结束任务招募");
            message.setTime(new Date());
            message.setUserId(uid);
            messageMapper.insert(message);
        }
    }

    // 需要得到reportId对应的测试任务的所有测试报告，并分成三个sample
    public TreeMap<Integer, List<ReportReview>> getCaptureProcess(int reportId) {
        DefectReport defectReport = defectReportMapper.selectByPrimaryKey((long) reportId); // 获取评审记录对应的报告
        TestRecord testRecord = testRecordMapper.selectByPrimaryKey(defectReport.getTestRecordId());    // 获取报告对应的测试记录
        List<TestRecord> testRecordList = testRecordMapper.selectByTaskId(testRecord.getTaskId());      // 获取任务对应的所有测试记录
        // 获取测试记录对应的所有通过审核的报告
        List<ReportReview> reportReviewList = new ArrayList<>();
        for (TestRecord testRecord1 : testRecordList) {
            List<DefectReport> defectReports = defectReportMapper.selectByTestRecordId(testRecord1.getId());    // 获取测试记录对应的所有缺陷报告
            for (DefectReport defectReport1 : defectReports) {    // 获取缺陷报告对应的评审记录
                if (!defectReport1.getStatus().equals("PASSED")) {
                    continue;
                }
                List<ReportReview> reportReviewList1 = reportReviewMapper.selectByReportId(defectReport1.getId());
                if (reportReviewList1 == null || reportReviewList1.size() == 0) {
                    continue;
                } else {
                    ReportReview reportReview = reportReviewList1.get(0);
                    reportReviewList.add(reportReview);
                }
            }
        }
        reportReviewList = reportReviewList.stream().sorted(Comparator.comparing(ReportReview::getCreateTime)).collect(Collectors.toList());    // 按时间对评审报告排序

        if (reportReviewList.size() < 15) {      // 过少，任务肯定无法结束
            return null;
        }

        int sampleSize = (int) Math.ceil(1.0 * reportReviewList.size() / 3);
        TreeMap<Integer, List<ReportReview>> treeMap = new TreeMap<>();
        for (int i = 0; i < reportReviewList.size(); i++) {
            List<ReportReview> list = new ArrayList<>();
            for (int j = 0; j < sampleSize; j++) {
                list.add(reportReviewList.get(j));
            }
            i += sampleSize;
            treeMap.put(i / sampleSize, list);
        }
        return treeMap;
    }

    public List<DupBug> getDupBugList(int reportId) {
        DefectReport defectReport = defectReportMapper.selectByPrimaryKey((long) reportId); // 获取评审记录对应的报告
        TestRecord testRecord = testRecordMapper.selectByPrimaryKey(defectReport.getTestRecordId());    // 获取缺陷报告对应的测试记录
        List<TestRecord> testRecordList = testRecordMapper.selectByTaskId(testRecord.getTaskId());      // 获取任务对应的所有测试记录
        List<DupBug> dupBugList = new ArrayList<>();
        for (TestRecord testRecord1 : testRecordList) {
            List<DefectReport> defectReports = defectReportMapper.selectByTestRecordId(testRecord1.getId());    // 获取测试记录对应的所有缺陷报告
            for (DefectReport defectReport1 : defectReports) {
                if (!defectReport1.getStatus().equals("PASSED")) {
                    continue;
                }
                DupBug dupBug = dupBugMapper.selectByReportid(defectReport1.getId().intValue());
                if (dupBug != null) {
                    dupBugList.add(dupBug);
                }
            }
        }
        return dupBugList;
    }

    @Override
    public ResultVo<List<DefectReportVo>> getDistinctBugReport(Integer defectReportId) {
        DefectReport defectReport = defectReportMapper.selectByPrimaryKey(defectReportId.longValue());  // 获取缺陷报告
        Long testRecordId = defectReport.getTestRecordId();
        TestRecord testRecord = testRecordMapper.selectByPrimaryKey(testRecordId);  // 获取缺陷报告对应的测试记录
        Long taskId = testRecord.getTaskId();       // 获取测试记录对应的任务
        List<TestRecord> testRecordList = testRecordMapper.selectByTaskId(taskId);
        List<DefectReport> defectReports = new ArrayList<>();
        for (TestRecord testRecord1 : testRecordList) {
            List<DefectReport> defectReports1 = defectReportMapper.selectByTestRecordId(testRecord1.getId());
            if (defectReports1 != null && defectReports1.size() != 0)
                defectReports.addAll(defectReports1);   // 得到这个任务的所有缺陷报告
        }

        List<DefectReportVo> resultList = new ArrayList<>();
        for (DefectReport defectReport1 : defectReports) {
            List<ReportReview> reportReviewList = reportReviewMapper.selectByReportId(defectReport1.getId());
            if (reportReviewList == null || reportReviewList.size() == 0) {
                continue;
            } else {
                ReportReview reportReview = reportReviewList.get(0);    // 获取评审记录
                if (reportReview.getAccepted() && !reportReview.getIsRepeatBug()) {  // 该报告被接受并且不是重复报告
                    resultList.add(new DefectReportVo(defectReport1));
                }
            }
        }
        if (resultList.size() != 0) {
            resultList = resultList.stream().sorted(Comparator.comparing(DefectReportVo::getId)).collect(Collectors.toList());
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "返回的第一次返现bug的报告如下", resultList);
        } else return new ResultVo<>(Constant.REQUEST_SUCCESS, "之前提交的报告中还没有发现bug的哦，请您再仔细审查下");
    }

}
