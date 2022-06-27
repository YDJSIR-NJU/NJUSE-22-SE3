package com.assignment.collect.dto.TaskRecommendStrategyDTO;

import com.assignment.collect.service.recommend.strategy.TaskRecommendStrategy;

import java.util.Map;

/**
 * @Author: XiaYu
 * @Date 2022/3/26 21:44
 */
public class TaskRecommendStrategyDTO {
    public Integer strategyId;
    public Map<String, Double> config;
    public String description;
    public boolean inUse;
    public int N;

    public TaskRecommendStrategyDTO() {

    }

    public TaskRecommendStrategyDTO(TaskRecommendStrategy taskRecommendStrategy) {
        this.strategyId = taskRecommendStrategy.getStrategyId();
        this.config = taskRecommendStrategy.getConfig();
        this.description = taskRecommendStrategy.getDescription();
        this.inUse = taskRecommendStrategy.isInUse();
        this.N = TaskRecommendStrategy.N;
    }
}
