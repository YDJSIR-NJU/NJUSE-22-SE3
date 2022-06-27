package com.assignment.collect.vo.testRecord;

import com.assignment.collect.po.testRecord.TestRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: MJW
 * @Date 2022/2/26 21:43
 */
@Getter
@Setter
public class TestRecordVo {
    private Long id;

    private Long taskId;

    private Long userId;

    private Boolean finished;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commitTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    public TestRecordVo() {

    }

    public TestRecordVo(TestRecord testRecord) {
        this.commitTime = testRecord.getCommitTime();
        this.finished = testRecord.getFinished();
        this.finishTime = testRecord.getFinishTime();
        this.id = testRecord.getId();
        this.taskId = testRecord.getTaskId();
        this.userId = testRecord.getUserId();

    }

}
