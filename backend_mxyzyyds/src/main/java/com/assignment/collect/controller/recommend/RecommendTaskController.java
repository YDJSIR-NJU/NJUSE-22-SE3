package com.assignment.collect.controller.recommend;

import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.service.recommend.RecommendTaskService;
import com.assignment.collect.util.AdminAuthentication;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 22:55
 */
@RestController
@RequestMapping("/recommend")
public class RecommendTaskController {

    @Autowired
    RecommendTaskService recommendTaskService;

    @ApiOperation("getRecommendations")
    @GetMapping("/getRecommendations")
    public ResultVo<List<TaskVo>> getRecommendations(@RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return recommendTaskService.getRecommendations(uid);
    }

    @ApiOperation("changeStrategy")
    @GetMapping("/changeStrategy")
    public ResultVo<TaskRecommendStrategyDTO> changeStrategy(@RequestParam Integer strategyId, HttpServletRequest httpServletRequest) {
        if (!AdminAuthentication.isAdmin(httpServletRequest)) {
            return null;
        }
        assert strategyId != null;
        return recommendTaskService.changeStrategy(strategyId);
    }

    @ApiOperation("modifyStrategy")
    @PostMapping("/modifyStrategy")
    public ResultVo<TaskRecommendStrategyDTO> modifyStrategy(@RequestBody Map<String, Double> config, @RequestParam Integer strategyId, @RequestParam Integer N, HttpServletRequest httpServletRequest) {
        if (!AdminAuthentication.isAdmin(httpServletRequest)) {
            return null;
        }
        return recommendTaskService.modifyStrategy(config, strategyId, N);
    }


    @ApiOperation("getStrategies")
    @GetMapping("/getStrategies")
    public ResultVo<List<TaskRecommendStrategyDTO>> getStrategies() {
        return recommendTaskService.getStrategies();
    }
}
