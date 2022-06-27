package com.assignment.collect.controller.task;


import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.service.task.TaskService;
import com.assignment.collect.util.AdminAuthentication;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import com.assignment.collect.vo.testRecord.TestRecordVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/2/24 11:47
 */

@RestController
@RequestMapping("/task")
@Api(tags = "TaskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("getTaskList")
    @GetMapping("/list/{page}")
    public PageInfo<TaskVo> getTaskList(@PathVariable Integer page, @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.getTaskList(page, Constant.PAGE_SIZE, uid);
    }


    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("published")
    @GetMapping("/published/{uid}")
    public PageInfo<TaskVo> getTasksByUid(@RequestParam Integer page,
                                          @PathVariable Integer uid,
                                          HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.getTasksByUid(page, Constant.PAGE_SIZE, uid);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("processing")
    @GetMapping("/processing/{uid}")
    public PageInfo<TaskVo> getTasksProcessingByuid(@RequestParam Integer page,
                                                    @PathVariable Integer uid,
                                                    HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.getTasksProcessingByuid(page, Constant.PAGE_SIZE, uid);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("history")
    @GetMapping("/history/{uid}")
    public PageInfo<TaskVo> getTasksHistoryByuid(@RequestParam Integer page,
                                                 @PathVariable Integer uid,
                                                 HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.getTasksHistoryByuid(page, Constant.PAGE_SIZE, uid);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("all")
    @GetMapping("/all/{uid}")
    public PageInfo<TaskVo> getAll(@RequestParam Integer page,
                                   @PathVariable Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        if (!AdminAuthentication.isAdmin(httpServletRequest)) {
            return null;
        }

        return taskService.getAll(page, Constant.PAGE_SIZE, uid);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("select")
    @PostMapping("/select")
    public ResultVo<TestRecordVo> accept(@RequestParam Integer taskid,
                                         @RequestParam Integer uid,
                                         HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.accept(taskid, uid);
    }

    @ApiOperation("check")
    @PostMapping("/check")
    public ResultVo<TestRecordVo> check(@RequestParam Integer taskid,
                                        @RequestParam Integer uid,
                                        HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.check(taskid, uid);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @ApiOperation("detail")
    @GetMapping("/detail")
    public ResultVo<TaskVo> getDetail(@RequestParam Integer uid,
                                      @RequestParam Integer taskid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return taskService.getDetail(uid, taskid);
    }

    @ApiOperation("testRecord")
    @GetMapping("/testRecord")
    public ResultVo<TestRecordVo> getTestRecord(@RequestParam Integer uid,
                                                @RequestParam Integer testrecordid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);
        return taskService.getTestRecord(uid, testrecordid);
    }

    @ApiOperation("release")
    @PostMapping(value = "/release")
    public ResultVo<TaskVo> release(@RequestParam String taskVo, @RequestPart MultipartFile[] files) {
        TaskVo object = JSONObject.parseObject(taskVo, TaskVo.class);
        return taskService.releaseTask(object, files);
    }

    @ApiOperation("downloadDocs")
    @GetMapping("/downloadDocs")
    public void downloadDocs(@RequestParam Integer userId, @RequestParam Integer taskId, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        userId = Integer.parseInt(subject.split("_")[0]);

        taskService.downloadDocs(userId, taskId, response);
    }

    /**
     * 发包方获取推荐的用户
     */
    @ApiOperation("getRecommendations")
    @GetMapping("/getRecommendations")
    public List<Integer> getRecommendations(Integer taskId) {  // 返回值是用户id的列表
        return taskService.getRecommendations(taskId);
    }


    /**
     * 允许发包方手动停止某个任务的招募
     */
    @ApiOperation("closeTask")
    @GetMapping("/close")
    public ResultVo<TaskVo> closeTask(@RequestParam Integer taskId) {
        return taskService.closeTask(taskId);
    }

    @ApiOperation("checkIsClosed")
    @GetMapping("/checkIsClosed")
    public ResultVo<Boolean> checkIsClosed(@RequestParam Integer taskId){
        return taskService.checkIsClosed(taskId);
    }
}
