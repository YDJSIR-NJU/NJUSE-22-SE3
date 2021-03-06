package com.assignment.collect.serviceimpl.similarity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.dao.defectReport.DefectReportMapper;
import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.dao.reportDescriptor.ReportDescriptorMapper;
import com.assignment.collect.dao.similarity.SimilarityMapper;
import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.dao.userDescriptor.UserDescriptorMapper;
import com.assignment.collect.dataProcess.WordSegment;
import com.assignment.collect.po.defectReport.DefectReport;
import com.assignment.collect.po.message.Message;
import com.assignment.collect.po.reportDescriptor.ReportDescriptor;
import com.assignment.collect.po.similarity.Similarity;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.po.userDescriptor.UserDescriptor;
import com.assignment.collect.service.similarity.SimilarityService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.defectReport.DefectReportVo;
import com.assignment.collect.vo.python.SimilarityCalcFormVO;
import com.assignment.collect.vo.python.SimilarityCalcRetVO;
import com.assignment.collect.vo.similarity.AuditDetailsVo;
import com.assignment.collect.vo.similarity.SimilarityConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.assignment.collect.util.Constant.PYTHON_BACKEND;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:53
 */
@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Autowired
    TestRecordMapper testRecordMapper;

    @Autowired
    DefectReportMapper defectReportMapper;

    @Autowired
    SimilarityMapper similarityMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    UserCapabilityMapper userCapabilityMapper;

    @Autowired
    UserDescriptorMapper userDescriptorMapper;

    @Autowired
    ReportDescriptorMapper reportDescriptorMapper;

    // ????????????
    @Async
    @Override
    public void calSim(DefectReport defectReport) {
        TestRecord testRecord = testRecordMapper.selectByPrimaryKey(defectReport.getTestRecordId());
        List<DefectReport> lists = defectReportMapper.selectByTaskId(testRecord.getTaskId());
        if (lists.size() > 0) {
            int index = 0;
            for (int i = 0; i < lists.size(); i++) {
                if (Objects.equals(lists.get(i).getId(), defectReport.getId())) index = i;
            }
            lists.remove(index); // ????????????????????????????????????????????????????????????????????????
            DefectReport[] reports = new DefectReport[lists.size()];




            /*
                TF-IDF ??????????????????????????????????????????
             */
            if (lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) reports[i] = lists.get(i);
                SimilarityCalcRetVO TFIDF = calcSimilarity(defectReport, reports, "TF-IDF");
                Optional<Double> max = Arrays.stream(TFIDF.getScore()).max(Comparator.comparingDouble(t -> t));
                if (Double.compare(max.get(), Constant.REJECTEDSCORE) > 0) {
                    defectReport.setStatus("REJECTED"); // ????????????????????????
                } else {
                    defectReport.setStatus("AUDITING");//???????????????????????????

                    /**
                     * todo
                     */
                    // ??????????????????????????????????????????????????????????????????????????????????????????
                    System.out.println("???????????????");
                    calDescriptor(defectReport, testRecord);
                }
            } else {
                defectReport.setStatus("AUDITING"); // ???????????????????????????????????????
                calDescriptor(defectReport, testRecord);
            }


            /**
             * TODO ?????????????????????
             */
            UserCapability userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());
            if (userCapability == null) {
                userCapability = new UserCapability();
                userCapability.setUid(testRecord.getUserId());
                userCapabilityMapper.insert(userCapability);
                userCapability = userCapabilityMapper.selectByUserId(testRecord.getUserId());
            }
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
            userCapabilityMapper.updateByPrimaryKey(userCapability);

            /**
             * TODO Text2Vec ???????????????????????????????????????
             */
            if ((defectReport.getStatus().equals("PASSED") || defectReport.getStatus().equals("AUDITING")) && lists.size() != 0) {
                SimilarityCalcRetVO Text2Vec = calcSimilarity(defectReport, reports, "Text2Vec");
                int ListsSize = Text2Vec.getScore().length;
                for (int i = 0; i < ListsSize; i++) {
                    Similarity similarity = new Similarity();
                    similarity.setDefectReportId1(Long.valueOf(Text2Vec.getSrc_id()));
                    similarity.setDefectReportId2(Long.valueOf(Text2Vec.getDest_id()[i]));
                    similarity.setSimilarity(Text2Vec.getScore()[i]);
                    similarityMapper.insert(similarity);
                }
            }
            defectReportMapper.updateByPrimaryKey(defectReport); // ???????????????????????????????????????????????????????????????

            Message workerMessage = new Message();
            Long workerId = testRecord.getUserId();
            Long publisherId = taskMapper.selectByPrimaryKey(testRecord.getTaskId()).getUserId();
            workerMessage.setUserId(workerId);
            if ((defectReport.getStatus().equals("PASSED") || defectReport.getStatus().equals("AUDITING"))) {
                workerMessage.setContent("?????????????????????" + defectReport.getId() + "??????????????????????????????????????????????????????????????????");
                Message publisherMessage = new Message();
                publisherMessage.setUserId(publisherId);
                publisherMessage.setContent("????????????" + testRecord.getTaskId() + "???????????????????????????????????????" + "???????????????" + defectReport.getId());
                messageMapper.insert(publisherMessage);
            } else {
                workerMessage.setContent("?????????????????????" + defectReport.getId() + "????????????????????????????????????");
            }
            messageMapper.insert(workerMessage);
        }
    }

    @Override
    public ResultVo<List<AuditDetailsVo>> findAuditDetails(Integer reportId, Integer topN) {
        List<AuditDetailsVo> lists = new ArrayList<>();
        DefectReport udefectReport = defectReportMapper.selectByPrimaryKey(Long.valueOf(reportId));
        if (!udefectReport.getStatus().equals("PASSED")) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "????????????????????????", lists);
        } else {
            List<Similarity> similarities = similarityMapper.selectByReportId(Long.valueOf(reportId));//
            // ??????????????????????????????????????????????????????,??????????????????report1???????????????????????????report2?????????

            if (similarities.size() > 0) {
                Iterator<Similarity> similarityIterator = similarities.iterator();
                while (similarityIterator.hasNext()) {
                    DefectReport defectReport;
                    Similarity similarity = similarityIterator.next();
                    DefectReport defectReport2 = defectReportMapper.selectByPrimaryKey((long) similarity.getDefectReportId2().intValue());
                    DefectReport defectReport1 = defectReportMapper.selectByPrimaryKey((long) similarity.getDefectReportId1().intValue());
                    if (defectReport1.getId().intValue() - reportId == 0) defectReport = defectReport2;
                    else defectReport = defectReport1;
                    if (!defectReport.getStatus().equals("PASSED")) {
                        similarityIterator.remove(); // ????????????????????????
                    }
                }
                if (similarities.size() > 0) {
                    List<Similarity> res = findTopN(similarities, topN);
                    for (Similarity similarity : res) {
                        DefectReport defectReport;
                        DefectReport defectReport2 = defectReportMapper.selectByPrimaryKey((long) similarity.getDefectReportId2().intValue());
                        DefectReport defectReport1 = defectReportMapper.selectByPrimaryKey((long) similarity.getDefectReportId1().intValue());
                        if (defectReport1.getId().intValue() - reportId == 0) defectReport = defectReport2;
                        else defectReport = defectReport1;
                        AuditDetailsVo auditDetailsVo = new AuditDetailsVo();
                        auditDetailsVo.setDefectReportVo(new DefectReportVo(defectReport));
                        auditDetailsVo.setScore(similarity.getSimilarity());
                        lists.add(auditDetailsVo);
                    }
                    return new ResultVo<>(Constant.REQUEST_SUCCESS, "????????????", lists);
                } else {
                    return new ResultVo<>(Constant.REQUEST_FAIL, "???????????????", lists);
                }
            } else return new ResultVo<>(Constant.REQUEST_FAIL, "???????????????", lists);
        }
    }

    public SimilarityCalcRetVO calcSimilarity(DefectReport defectReport, DefectReport[] defectReports, String type) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        SimilarityCalcFormVO similarityCalcFormVO = new SimilarityCalcFormVO();
        similarityCalcFormVO.setDefect_report(defectReport);
        similarityCalcFormVO.setDefect_reports(defectReports);
        HttpEntity<SimilarityCalcFormVO> httpEntity = new HttpEntity<>(similarityCalcFormVO, headers);

        ResponseEntity<SimilarityCalcRetVO> result = restTemplate.exchange("http://" + PYTHON_BACKEND + "/calc/" + Constant.similarityAlgorithms.get(type), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<SimilarityCalcRetVO>() {
        });
        SimilarityCalcRetVO similarityCalcRetVO = result.getBody();
        assert similarityCalcRetVO != null;
        return similarityCalcRetVO;
    }

    @Override
    public ResultVo<List<AuditDetailsVo>> recommend(Integer uid,
                                                    Integer reportId,
                                                    Integer topN) {
        ResultVo<List<AuditDetailsVo>> resultVo = findAuditDetails(reportId, topN);
        if (Objects.equals(resultVo.getCode(), Constant.REQUEST_SUCCESS)) {
            List<AuditDetailsVo> before = resultVo.getData();
            before.removeIf(auditDetailsVo -> Double.compare(auditDetailsVo.getScore(), Constant.COOPERATIONMIN) < 0);
            if (before.size() > 0 && before.size() == topN) {
                resultVo.setMsg("????????????????????????");
                resultVo.setData(before);
                return resultVo;
            } else if (before.size() > 0 && before.size() < topN) {
                resultVo.setMsg("????????????????????????,???????????????????????????????????????" + topN);
                resultVo.setData(before);
                return resultVo;
            } else {
                resultVo.setCode(Constant.REQUEST_FAIL);
                resultVo.setMsg("?????????????????????");
                resultVo.setData(before);
            }
        }

        return resultVo;

    }

    public List<Similarity> findTopN(List<Similarity> lists, Integer topN) {
        Collections.sort(lists);
        List<Similarity> res = new ArrayList<>();
        if (lists.size() <= topN) return lists;
        else {
            for (int i = 0; i < topN; i++) {
                res.add(lists.get(i));
            }
        }
        return res;

    }

    @Override
    public ResultVo<SimilarityConfigVo> getLimit(Integer uid) {
        SimilarityConfigVo similarityConfigVo = new SimilarityConfigVo();
        similarityConfigVo.setRejectedScore(Constant.REJECTEDSCORE);
        similarityConfigVo.setCooperationMin(Constant.COOPERATIONMIN);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "????????????", similarityConfigVo);
    }

    @Override
    public ResultVo<SimilarityConfigVo> setLimit(SimilarityConfigVo similarityConfigVo,
                                                 Integer uid) {
        Constant.REJECTEDSCORE = similarityConfigVo.getRejectedScore();
        Constant.COOPERATIONMIN = similarityConfigVo.getCooperationMin();
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "????????????", similarityConfigVo);
    }

    public void calDescriptor(DefectReport defectReport, TestRecord testRecord) {
        /**
         * todo
         */
        // ??????????????????????????????????????????????????????????????????????????????????????????
        System.out.println("???????????????");
        List<String> strings = WordSegment.segmentWord(defectReport.getDescription());
        Set<String> words = new HashSet<>(strings);
        strings = new ArrayList<>(words);

        UserDescriptor userDescriptor = userDescriptorMapper.selectByUid(testRecord.getUserId().intValue());
        HashMap<String, Integer> descriptorMap;
        if (userDescriptor == null) {
            userDescriptor = new UserDescriptor();
            userDescriptor.setUserid(testRecord.getUserId().intValue());
            descriptorMap = new HashMap<>();
        } else {
            descriptorMap = JSONObject.parseObject(userDescriptor.getWords(), HashMap.class);
        }
        for (String word : words) {
            // ???????????????????????????????????????????????????
            descriptorMap.put(word, descriptorMap.getOrDefault(word, 0) + 1);
        }
        // ????????????????????????
        userDescriptor.setWords(JSON.toJSONString(descriptorMap));
        if (userDescriptorMapper.selectByUid(testRecord.getUserId().intValue()) != null) {
            userDescriptorMapper.updateByPrimaryKey(userDescriptor);
        } else {
            userDescriptorMapper.insert(userDescriptor);
        }

        // ????????????????????????
        ReportDescriptor reportDescriptor = new ReportDescriptor();
        reportDescriptor.setReportid(defectReport.getId().intValue());
        reportDescriptor.setWords(JSON.toJSONString(strings));
        reportDescriptorMapper.insert(reportDescriptor);
    }
}
