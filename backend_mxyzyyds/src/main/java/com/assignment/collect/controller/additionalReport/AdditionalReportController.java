package com.assignment.collect.controller.additionalReport;

import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.service.additionalReport.AdditionalReportService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.additionalReport.AdditionalReportVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:46
 */
@RestController
@RequestMapping("/additionalReport")
@Api(tags = "AdditionalReportController")
public class AdditionalReportController {

    @Autowired
    private AdditionalReportService additionalReportService;

    @ApiOperation("detail")
    @GetMapping("/detail")
    public ResultVo<AdditionalReportVo> getDetail(@RequestParam Integer uid, @RequestParam Integer additionalReportId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        assert uid != null;
        assert additionalReportId != null;
        return additionalReportService.getDetail(uid, additionalReportId);
    }

    @ApiOperation("release")
    @PostMapping(value = "/release")
    public ResultVo<AdditionalReportVo> releaseAdditionalReport(@RequestParam String additionalReportVo, @RequestPart MultipartFile[] files) {
        AdditionalReportVo object = JSONObject.parseObject(additionalReportVo, AdditionalReportVo.class);
        return additionalReportService.releaseAdditionalReport(object, files);
    }

    @ApiOperation("query")
    @GetMapping("/query/{defectReportId}")
    public PageInfo<AdditionalReportVo> getReportsByReportId(@PathVariable Integer defectReportId, @RequestParam Integer page,
                                                             @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        assert defectReportId != null;
        return additionalReportService.getAdditionalReportsByReportId(page, Constant.PAGE_SIZE, uid, defectReportId);
    }

    @ApiOperation("query")
    @GetMapping("/queryMy/{defectReportId}")
    public PageInfo<AdditionalReportVo> getReportsByReportIdAndUid(@PathVariable Integer defectReportId, @RequestParam Integer page,
                                                                   @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return additionalReportService.getAdditionalReportsByReportIdAndUid(page, Constant.PAGE_SIZE, uid, defectReportId);
    }
}
