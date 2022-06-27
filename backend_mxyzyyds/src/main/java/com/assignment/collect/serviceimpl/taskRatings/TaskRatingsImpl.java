package com.assignment.collect.serviceimpl.taskRatings;

import com.assignment.collect.dao.taskRatings.TaskRatingsMapper;
import com.assignment.collect.po.taskRatings.TaskRatings;
import com.assignment.collect.service.taskRatings.TaskRatingsService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.taskRatings.TaskRatingsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:54
 */
@Service
public class TaskRatingsImpl implements TaskRatingsService {

    @Autowired
    TaskRatingsMapper taskRatingsMapper;

    @Override
    public ResultVo<TaskRatingsVo> rank(TaskRatingsVo taskRatingsVo) {
        TaskRatings taskRatings = new TaskRatings(taskRatingsVo);
        if (taskRatingsMapper.selectByPrimaryKey(taskRatings.getUserId(), taskRatings.getTaskId()) != null) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "请勿重复对同一个任务评分");
        }
        taskRatingsMapper.insert(taskRatings);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "评价成功", new TaskRatingsVo(taskRatings));
    }

    @Override
    public ResultVo<TaskRatingsVo> getRatings(Integer uid, Integer taskid) {
        TaskRatings taskRatings = taskRatingsMapper.selectByPrimaryKey((long) uid, (long) taskid);
        if (taskRatings == null) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "该用户尚未对该任务评分", null);
        }
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "查询成功", new TaskRatingsVo(taskRatings));
    }

    @Override
    public PageInfo<TaskRatingsVo> getTaskRatings(Integer currPage, Integer pageSize, Integer uid, Integer taskid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<TaskRatings> po = new PageInfo<>(taskRatingsMapper.selectByTaskId((long) taskid));
        return getTaskRatingsVoPageInfo(uid, po);
    }

    @Override
    public ResultVo<Double> getTaskRatingPer(Integer uid, Integer taskid) {
        List<TaskRatings> lists = taskRatingsMapper.selectByTaskId((long) taskid);
        int size = lists.size();
        Long tatalScore = Long.valueOf(0);
        Double per = 0.0;
        if (size > 0) {
            for (TaskRatings tr : lists) {
                tatalScore += tr.getScore();
            }
            per = (tatalScore + 0.0) / size;
            return new ResultVo<Double>(Constant.REQUEST_SUCCESS, "查询任务均分成功！", per);
        } else return new ResultVo<Double>(Constant.REQUEST_FAIL, "此任务暂无评分！", per);
    }

    PageInfo<TaskRatingsVo> getTaskRatingsVoPageInfo(Integer uid, PageInfo<TaskRatings> po) {
        return PageInfoUtil.convert(po, TaskRatingsVo.class);
    }
}
