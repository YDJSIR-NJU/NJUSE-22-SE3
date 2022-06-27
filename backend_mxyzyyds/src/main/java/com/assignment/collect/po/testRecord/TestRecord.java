package com.assignment.collect.po.testRecord;

import com.assignment.collect.vo.testRecord.TestRecordVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:MJW
 * @Date
 */
public class TestRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long taskId;
    private Long userId;
    private Boolean finished;
    private Date commitTime;
    private Date finishTime;

    public TestRecord() {
    }

    public TestRecord(TestRecordVo testRecordVo) {
        this.commitTime = testRecordVo.getCommitTime();
        this.finished = testRecordVo.getFinished();
        this.userId = testRecordVo.getUserId();
        this.id = testRecordVo.getId();
        this.taskId = testRecordVo.getTaskId();
        this.finishTime = testRecordVo.getFinishTime();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
