package com.assignment.collect.controller.userCapability;

import com.assignment.collect.dto.UserCapabilityDTO.UserCapabilityDTO;
import com.assignment.collect.service.defectReport.DefectReportService;
import com.assignment.collect.service.userCapability.UserCapabilityService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.defectReport.DevVo;
import com.assignment.collect.vo.userCapability.TypeVo;
import com.assignment.collect.vo.userCapability.UserCapabilityVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/usercapability")
@Api(tags = "UserCapability")
public class userCapabilityController {
    @Autowired
    private UserCapabilityService userCapabilityService;

    @Autowired
    private DefectReportService defectReportService;

    @ApiOperation("detail")
    @GetMapping("/detail")
    public ResultVo<UserCapabilityVo> getDetail(@RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        assert uid != null;

        return userCapabilityService.getDetail(uid);
    }

    @ApiOperation("list")
    @GetMapping("/list")
    public PageInfo<UserCapabilityDTO> getList(@RequestParam Integer uid,
                                               @RequestParam Integer page,
                                               HttpServletRequest httpServletRequest) {
        /**
         * TODO 发包方角色验证
         */
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        assert uid != null;
        return userCapabilityService.getList(uid, page, Constant.PAGE_SIZE);
    }

    @ApiOperation("updateall")
    @PostMapping(value = "/updateall")
    public ResultVo<List<UserCapabilityDTO>> updateAllWk(@RequestParam Integer uid,
                                                         HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        return userCapabilityService.updateAll(uid);

    }

    @ApiOperation("queryType")
    @GetMapping("/queryType")
    public ResultVo<TypeVo> getTasksTypeByUserId(@RequestParam Integer uid,
                                                 HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        return userCapabilityService.getTasksTypeByUserId(uid);
    }

    @ApiOperation("queryDev")
    @GetMapping("/queryDev")
    public ResultVo<DevVo> getReportsDevByUserId(@RequestParam Integer uid,
                                                 HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        return defectReportService.getReportsDevByUserId(uid);
    }

    @ApiOperation("getProbPref")
    @GetMapping("/getProbPref")
    public ResultVo<Double> getProbPref(@RequestParam Integer uid, @RequestParam String term,
                                        HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        return userCapabilityService.getProbPref(uid, term);
    }


}
