package com.assignment.collect.vo.taskRatings;

import com.assignment.collect.po.taskRatings.TaskRatings;
import lombok.Data;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 13:59
 */
@Data
public class TaskRatingsVo {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long taskId;
    private Integer score;

    public TaskRatingsVo() {

    }

    public TaskRatingsVo(TaskRatings taskRatings) {
        this.userId = taskRatings.getUserId();
        this.taskId = taskRatings.getTaskId();
        this.score = taskRatings.getScore();
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
