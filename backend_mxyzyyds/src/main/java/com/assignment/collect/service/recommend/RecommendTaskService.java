package com.assignment.collect.service.recommend;

import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 22:55
 */
public interface RecommendTaskService {
    ResultVo<List<TaskVo>> getRecommendations(Integer uid);

    ResultVo<TaskRecommendStrategyDTO> changeStrategy(Integer strategyId);

    ResultVo<TaskRecommendStrategyDTO> modifyStrategy(Map<String, Double> config, Integer strategyId, Integer N);

    ResultVo<List<TaskRecommendStrategyDTO>> getStrategies();
}
