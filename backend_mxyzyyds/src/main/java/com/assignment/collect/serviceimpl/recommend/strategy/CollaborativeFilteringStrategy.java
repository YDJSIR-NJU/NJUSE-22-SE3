package com.assignment.collect.serviceimpl.recommend.strategy;

import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.service.recommend.strategy.TaskRecommendStrategy;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.python.TaskRecommendFormVo;
import com.assignment.collect.vo.python.TaskRecommendRetVo;
import com.assignment.collect.vo.task.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 23:02
 */
@Component
public class CollaborativeFilteringStrategy extends TaskRecommendStrategy {

    public static final int K = 10;     // 皮尔逊相关系数寻找的用户数

    @Autowired
    TaskMapper taskMapper;

    public CollaborativeFilteringStrategy() {
        super();
        inUse = false;   //默认禁用
        this.strategyId = 2;
        this.description = "本推荐算法是基于用户的协同过滤推荐算法，会选取一部分与该用户最接近的用户，依据这部分用户对任务的评分预测此用户对任务的评分，然后返回评分最高的前N个任务";
    }

    public List<TaskVo> recommend(Integer uid) {
        // 调用Python 后端API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TaskRecommendFormVo taskRecommendFormVO = new TaskRecommendFormVo();
        taskRecommendFormVO.setK(K);
        taskRecommendFormVO.setN(N);
        taskRecommendFormVO.setUser_id(uid);
        HttpEntity<TaskRecommendFormVo> httpEntity = new HttpEntity<>(taskRecommendFormVO, headers);
        ResponseEntity<TaskRecommendRetVo> result =
                restTemplate.exchange("http://" + Constant.PYTHON_BACKEND + "/calc/taskRecommend", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<TaskRecommendRetVo>() {
                        });
        TaskRecommendRetVo taskRecommendRetVO = result.getBody();
        assert taskRecommendRetVO != null;

        List<TaskVo> taskVos = new LinkedList<>();
        for (int taskId : taskRecommendRetVO.getRecommend_task()) {
            Task task = taskMapper.selectByPrimaryKey((long) taskId);
            taskVos.add(new TaskVo(task));
        }
        return taskVos;
    }

    @Override
    public ResultVo<TaskRecommendStrategyDTO> modify(HashMap<String, Double> newConfig, Integer N) {
        TaskRecommendStrategy.N = N;
        super.storeConfig();
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "修改成功");
    }
}
