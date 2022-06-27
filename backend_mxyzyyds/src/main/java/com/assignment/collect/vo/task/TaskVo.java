package com.assignment.collect.vo.task;

import com.assignment.collect.po.task.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/2/24 11:50
 */
@Data
public class TaskVo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String exeFilePath;
    private String discribFilePath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
    private Integer totalNum;
    private Integer currentNum;
    private String type;
    private Long userId;
    private String taskDiscribe;
    private String deviceBrand;
    private String operatingSystem;
    private Integer difficulty;
    //新增字段，用于表示任务的推荐度，只作为中间值与前端交互
    private double ratings;

    public TaskVo() {

    }

    public TaskVo(Task task) {
        this.id = task.getId();
        this.exeFilePath = task.getExeFilePath();
        this.discribFilePath = task.getDiscribFilePath();
        this.startTime = task.getStartTime();
        this.finishTime = task.getFinishTime();
        this.totalNum = task.getTotalNum();
        this.currentNum = task.getCurrentNum();
        this.type = task.getType();
        this.userId = task.getUserId();
        this.taskDiscribe = task.getTaskDiscribe();
        this.deviceBrand = task.getDeviceBrand();
        this.operatingSystem = task.getOperatingSystem();
        this.difficulty = task.getDifficulty();
    }
}
