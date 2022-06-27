package com.assignment.collect.controller.taskRatings;

import com.assignment.collect.service.taskRatings.TaskRatingsService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.taskRatings.TaskRatingsVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:47
 */
@RestController
@RequestMapping("/taskRating")
public class TaskRatingsController {

    @Autowired
    TaskRatingsService taskRatingsService;

    @ApiOperation("rank")
    @PostMapping("/rank")
    public ResultVo<TaskRatingsVo> rank(@RequestBody TaskRatingsVo taskRatingsVo) {
        return taskRatingsService.rank(taskRatingsVo);
    }

    @ApiOperation("getRatings")
    @GetMapping("/getRatings")
    public ResultVo<TaskRatingsVo> getRatings(@RequestParam Integer uid, @RequestParam Integer taskid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskRatingsService.getRatings(uid, taskid);
    }

    @ApiOperation("getTaskRatings")
    @GetMapping("/getTaskRatings")
    public PageInfo<TaskRatingsVo> getTaskRatings(@RequestParam Integer page, @RequestParam Integer uid, @RequestParam Integer taskid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskRatingsService.getTaskRatings(page, Constant.PAGE_SIZE, uid, taskid);
    }

    @ApiOperation("getTaskRatingPer")
    @GetMapping("/getTaskRatingPer")
    public ResultVo<Double> getTaskRatingPer(@RequestParam Integer uid,
                                             @RequestParam Integer taskid,
                                             HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);

        return taskRatingsService.getTaskRatingPer(uid, taskid);
    }

}
