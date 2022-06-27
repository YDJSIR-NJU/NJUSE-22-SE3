package com.assignment.collect.serviceimpl.recommend;

import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.service.recommend.RecommendTaskService;
import com.assignment.collect.service.recommend.strategy.TaskRecommendStrategy;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 22:56
 */
@Service
public class RecommendTaskServiceImpl implements RecommendTaskService {

    @Autowired
    TaskRecommendStrategy[] taskRecommendStrategy;

    public ResultVo<List<TaskVo>> getRecommendations(Integer uid) {
        List<TaskVo> list = new LinkedList<>();
        for (int i = 0; i < taskRecommendStrategy.length; i++) {
            if (taskRecommendStrategy[i].inUse) {
                list = taskRecommendStrategy[i].recommend(uid);
                break;
            }
        }
        //数据量不够也没办法，因为所有能参与计算的我这边都参与计算了
//        if (list.size() < TaskRecommendStrategy.N){     //数据量不够
//
//        }
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "推荐成功", list);
    }

    public ResultVo<TaskRecommendStrategyDTO> changeStrategy(Integer strategyId) {
        TaskRecommendStrategy res = null;
        for (TaskRecommendStrategy taskRecommendStrategy : taskRecommendStrategy) {
            if (taskRecommendStrategy.inUse) {
                taskRecommendStrategy.inUse = false;
                break;
            }
        }
        for (TaskRecommendStrategy taskRecommendStrategy : taskRecommendStrategy) {
            if (taskRecommendStrategy.getStrategyId().equals(strategyId)) {
                taskRecommendStrategy.inUse = true;
                res = taskRecommendStrategy;
                break;
            }
        }
        if (res == null) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "您想要切换的策略不存在");
        } else {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "推荐策略切换成功", new TaskRecommendStrategyDTO(res));
        }
    }

    public ResultVo<TaskRecommendStrategyDTO> modifyStrategy(Map<String, Double> config, Integer strategyId, Integer N) {
        for (TaskRecommendStrategy taskRecommendStrategy : taskRecommendStrategy) {
            if (taskRecommendStrategy.strategyId.equals(strategyId)) {
                return taskRecommendStrategy.modify((HashMap<String, Double>) config, N);
            }
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "您想要修改的策略并不存在");
    }

    public ResultVo<List<TaskRecommendStrategyDTO>> getStrategies() {
        List<TaskRecommendStrategyDTO> list = new LinkedList<>();
        for (TaskRecommendStrategy taskRecommendStrategy : taskRecommendStrategy) {
            list.add(new TaskRecommendStrategyDTO(taskRecommendStrategy));
        }
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "查询所有策略成功", list);
    }
}
