package com.assignment.collect.dto.ReportReviewDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReviewDTO {
    public Long id;

    public Long reportId;

    public Integer bugNums;

    public Double score;

    public Boolean isRepeatBug;

    public Boolean accepted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime;

    public Integer dupTag;  // 与哪一篇缺陷报告提出的bug重复
}
