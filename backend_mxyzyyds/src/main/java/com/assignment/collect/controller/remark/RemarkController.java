package com.assignment.collect.controller.remark;

import com.assignment.collect.service.remark.RemarkService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.remark.RemarkVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:46
 */
@RestController
@RequestMapping("/reportRemark")
public class RemarkController {

    @Autowired
    private RemarkService remarkService;

    @ApiOperation("release")
    @PostMapping("/release")
    public ResultVo<RemarkVo> releaseRemark(@RequestBody RemarkVo remarkVo, @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return remarkService.releaseRemark(remarkVo, uid);
    }

    @ApiOperation("query")
    @GetMapping("/query/{reportid}")
    public PageInfo<RemarkVo> getRemarksByReportId(@PathVariable Integer reportid, @RequestParam Integer uid, @RequestParam Integer page, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return remarkService.getRemarksByReportId(page, Constant.PAGE_SIZE, reportid, uid);
    }

    @ApiOperation("queryAvg")
    @GetMapping("/queryAvg")
    public ResultVo<Double> getAvgRemarkByReportId(@RequestParam Integer uid,
                                                   @RequestParam Integer reportid,
                                                   HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);

        return remarkService.getAvgRemarkByReportId(uid, reportid);
    }

}
