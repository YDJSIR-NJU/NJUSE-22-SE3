package com.assignment.collect.controller.defectReport;

import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.dto.ReportReviewDTO.ReportReviewDTO;
import com.assignment.collect.service.defectReport.DefectReportService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.cluster.ClusterVo;
import com.assignment.collect.vo.defectReport.DefectReportVo;
import com.assignment.collect.vo.reportReview.ReportReviewVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: YuDongjun
 * @date: 2022/3/1 21:45
 * @description:
 */
@RestController
@RequestMapping("/defectReport")
@Api(tags = "DefectReportController")
public class DefectReportController {

    @Autowired
    private DefectReportService defectReportService;

    @ApiOperation("detail")
    @GetMapping("/detail")
    public ResultVo<DefectReportVo> getDetail(@RequestParam Integer uid, @RequestParam Integer reportid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        assert reportid != null;
        return defectReportService.getDetail(uid, reportid);
    }

    @ApiOperation("release")
    @PostMapping(value = "/release")
    public ResultVo<DefectReportVo> releaseDefectReport(@RequestParam String defectReportVo, @RequestPart MultipartFile[] files) {
        DefectReportVo object = JSONObject.parseObject(defectReportVo, DefectReportVo.class);
        return defectReportService.releaseDefectReport(object, files);
    }

    @ApiOperation("query")
    @GetMapping("/query/{taskid}")
    public PageInfo<DefectReportVo> getReportsByTaskId(@PathVariable Integer taskid, @RequestParam Integer page,
                                                       @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);


        return defectReportService.getReportsByTaskid(page, Constant.PAGE_SIZE, uid, taskid);
    }

    @ApiOperation("fetch")
    @GetMapping("/fetch/{testRecordid}")
    public PageInfo<DefectReportVo> getReportsByTestRecordId(@PathVariable Integer testRecordid, @RequestParam Integer page,
                                                             @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        assert testRecordid != null;
        return defectReportService.getReportsByTestRecordId(page, Constant.PAGE_SIZE, uid, testRecordid);
    }

    @ApiOperation("getLowQuality")
    @GetMapping("/lowQuality")
    public PageInfo<DefectReportVo> getLowQualityReports(@RequestParam Integer taskId,
                                                         @RequestParam Integer page,
                                                         @RequestParam Integer uid,
                                                         HttpServletResponse response,
                                                         HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        userId = Integer.parseInt(subject.split("_")[0]);


        return defectReportService.getLowQualityReportsByTaskid(page, Constant.PAGE_SIZE, taskId);

    }

    /**
     * 发包方审查报告
     */
    @ApiOperation("review")
    @PostMapping(value = "/review")
    public ResultVo<DefectReportVo> reviewDefectReport(@RequestParam String reportReviewDto,
                                                       @RequestParam(required = false) Integer uid,
                                                       HttpServletResponse response,
                                                       HttpServletRequest httpServletRequest) {
//        System.out.println(reportReviewVo);
        ReportReviewDTO object = JSONObject.parseObject(reportReviewDto, ReportReviewDTO.class);

        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);

//        Integer defectReportId = Math.toIntExact(object.getReportId());
//        DefectReportVo defectReportVo = defectReportService.getDetail(uid, defectReportId).getData();
//        Long testRecordId = defectReportVo.getTestRecordId();


        /**
         * TODO 如何验证用户是发包方
         * */

        return defectReportService.reviewDefectReport(object);
    }

    @ApiOperation("getReview")
    @GetMapping("/getReview")
    public ResultVo<ReportReviewVo> getReviewByReportId(@RequestParam Integer uid,
                                                        @RequestParam Integer reportId,
                                                        HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        return defectReportService.getReviewByReportId(reportId);
    }

    @ApiOperation("getKMeans")
    @GetMapping("/getKMeans")
    public ResultVo<List<ClusterVo>> getKMeans(@RequestParam Integer uid, @RequestParam Integer k,
                                               @RequestParam Integer keyword,
                                               HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);


        return defectReportService.getKMeans(uid, k, keyword);
    }


    @ApiOperation("getWorkerKMeans")
    @GetMapping("/getWorkerKMeans")
    public ResultVo<List<ClusterVo>> getWorkerKMeans(@RequestParam Integer uid, @RequestParam Integer k,
                                                     @RequestParam Integer keyword, @RequestParam Integer taskid,
                                                     HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        return defectReportService.getWorkerKMeans(uid, k, keyword, taskid);
    }

    @ApiOperation("distinctBug")
    @GetMapping("/getDistinctReport")
    public ResultVo<List<DefectReportVo>> getDistinctBugReport(@RequestParam Integer defectReportId){
        return defectReportService.getDistinctBugReport(defectReportId);
    }
}
