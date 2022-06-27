package com.assignment.collect.service.taskRatings;

import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.taskRatings.TaskRatingsVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:50
 */
public interface TaskRatingsService {
    ResultVo<TaskRatingsVo> rank(TaskRatingsVo taskRatingsVo);

    ResultVo<TaskRatingsVo> getRatings(Integer uid, Integer taskid);

    PageInfo<TaskRatingsVo> getTaskRatings(Integer currPage, Integer pageSize, Integer uid, Integer taskid);

    ResultVo<Double> getTaskRatingPer(Integer uid, Integer taskid);
}
