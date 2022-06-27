package com.assignment.collect.po.task;

import com.assignment.collect.vo.task.TaskVo;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date startTime;
    private Date finishTime;
    private Integer totalNum;
    private Integer currentNum;
    private String type;
    private Long userId;
    private String deviceBrand;
    private String operatingSystem;
    private String exeFilePath;
    private String discribFilePath;
    private String taskDiscribe;
    private Integer difficulty;

    public Task() {

    }

    public Task(Long id, String exeFilePath, String discribFilePath,
                Date startTime, Date finishTime, Integer totalNum,
                Integer currentNum, String type, Long userId, String taskDiscribe, String operatingSystem, String deviceBrand, Integer difficulty) {
        this.id = id;
        this.discribFilePath = discribFilePath;
        this.exeFilePath = exeFilePath;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.totalNum = totalNum;
        this.currentNum = currentNum;
        this.type = type;
        this.userId = userId;
        this.taskDiscribe = taskDiscribe;
        this.operatingSystem = operatingSystem;
        this.deviceBrand = deviceBrand;
        this.difficulty = difficulty;
    }

    public Task(TaskVo taskVo) {
        this.id = taskVo.getId();
        this.exeFilePath = taskVo.getExeFilePath();
        this.discribFilePath = taskVo.getDiscribFilePath();
        this.startTime = taskVo.getStartTime();
        this.finishTime = taskVo.getFinishTime();
        this.totalNum = taskVo.getTotalNum();
        this.currentNum = taskVo.getCurrentNum();
        this.type = taskVo.getType();
        this.userId = taskVo.getUserId();
        this.taskDiscribe = taskVo.getTaskDiscribe();
        this.deviceBrand = taskVo.getDeviceBrand();
        this.operatingSystem = taskVo.getOperatingSystem();
        this.difficulty = taskVo.getDifficulty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getExeFilePath() {
        return exeFilePath;
    }

    public void setExeFilePath(String exeFilePath) {
        this.exeFilePath = exeFilePath;
    }

    public String getDiscribFilePath() {
        return discribFilePath;
    }

    public void setDiscribFilePath(String discribFilePath) {
        this.discribFilePath = discribFilePath;
    }

    public String getTaskDiscribe() {
        return taskDiscribe;
    }

    public void setTaskDiscribe(String taskDiscribe) {
        this.taskDiscribe = taskDiscribe;
    }

    public Integer getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
