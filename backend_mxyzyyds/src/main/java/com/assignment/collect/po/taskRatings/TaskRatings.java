package com.assignment.collect.po.taskRatings;

import com.assignment.collect.vo.taskRatings.TaskRatingsVo;

import java.io.Serializable;

public class TaskRatings implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long taskId;
    private Integer score;

    public TaskRatings() {

    }

    public TaskRatings(TaskRatingsVo taskRatingsVO) {
        this.userId = taskRatingsVO.getUserId();
        this.taskId = taskRatingsVO.getTaskId();
        this.score = taskRatingsVO.getScore();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
