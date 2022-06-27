package com.assignment.collect.vo.python;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRecommendRetVo {
    private Integer[] recommend_task;
    private Double time;
    private Integer user_id;
}
