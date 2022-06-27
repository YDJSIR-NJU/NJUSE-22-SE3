package com.assignment.collect.service.task;

import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import com.assignment.collect.vo.testRecord.TestRecordVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/2/24 14:54
 */
public interface TaskService {

    // 查询任务广场的任务
    PageInfo<TaskVo> getTaskList(Integer currPage, int pageSize, int uid);

    // 发布任务
    ResultVo<TaskVo> releaseTask(TaskVo taskVo, MultipartFile[] files);

    PageInfo<TaskVo> getTasksByUid(Integer currPage, Integer pageSize, Integer uid);

    PageInfo<TaskVo> getTasksProcessingByuid(Integer currPage, Integer pageSize, Integer uid);

    PageInfo<TaskVo> getTasksHistoryByuid(Integer page, Integer pageSize, Integer uid);

    PageInfo<TaskVo> getAll(Integer page, Integer pageSize, Integer uid);

    ResultVo<TestRecordVo> accept(Integer taskid, Integer uid);

    ResultVo<TestRecordVo> check(Integer taskid, Integer uid);

    ResultVo<TaskVo> getDetail(Integer uid, Integer taskid);

    ResultVo<TestRecordVo> getTestRecord(Integer uid, Integer testrecordid);

    void downloadDocs(Integer uid, Integer taskId, HttpServletResponse response);

    List<Integer> getRecommendations(Integer taskId);

    ResultVo<TaskVo> closeTask(Integer taskId);

    ResultVo<Boolean> checkIsClosed(Integer taskId);
}
