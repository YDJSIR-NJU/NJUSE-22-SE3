package com.assignment.collect.controller.similarity;

import com.assignment.collect.service.similarity.SimilarityService;
import com.assignment.collect.util.AdminAuthentication;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.similarity.AuditDetailsVo;
import com.assignment.collect.vo.similarity.SimilarityConfigVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:47
 */
@RestController
@RequestMapping("/Sim")

public class SimilarityController {

    @Autowired
    private SimilarityService similarityService;

    @ApiOperation("GetSimilarReportsWithoutLimit")
    @GetMapping("/cal")
    public ResultVo<List<AuditDetailsVo>> findAuditDetails(@RequestParam Integer uid,
                                                           @RequestParam Integer reportId,
                                                           @RequestParam Integer topN,
                                                           HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return similarityService.findAuditDetails(reportId, topN);

    }

    @ApiOperation("GetSimilarReportsWithLimit")
    @GetMapping("/recommand")
    public ResultVo<List<AuditDetailsVo>> recommend(@RequestParam Integer uid,
                                                    @RequestParam Integer reportId,
                                                    @RequestParam Integer topN,
                                                    HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return similarityService.recommend(uid, reportId, topN);
    }

    @ApiOperation("setLimit")
    @PostMapping("/setConfig")
    public ResultVo<SimilarityConfigVo> setLimit(@RequestBody SimilarityConfigVo similarityConfigVo,
                                                 @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        if (!AdminAuthentication.isAdmin(httpServletRequest)) {
            return null;
        }
        return similarityService.setLimit(similarityConfigVo, uid);
    }

    @ApiOperation("getLimit")
    @GetMapping("/getConfig")
    public ResultVo<SimilarityConfigVo> getLimit(@RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return similarityService.getLimit(uid);
    }

}
