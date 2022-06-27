package com.assignment.collect.serviceimpl.recommend.strategy;

import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.service.recommend.strategy.TaskRecommendStrategy;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.FileHelper;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 23:01
 */
@Component
public class AttributeStrategy extends TaskRecommendStrategy {

    public static final String path = (Constant.configDirectory + File.separator + Constant.recommendConfig1);

    @Autowired
    TaskMapper taskMapper;

    public AttributeStrategy() {
        super();
        inUse = true;   //默认启用
        loadConfig(path);
        this.strategyId = 1;
        this.description = "本推荐算法依据属性值是否匹配进行加权，会返回指定数量的属性值加权得分最高的前N个推荐的任务";
    }

    @Override
    public List<TaskVo> recommend(Integer uid) {
        List<Task> taskList = taskMapper.todoList();            //尚在招募的任务列表
        List<Task> historyTasks = taskMapper.selectHistoryByUid(Long.valueOf(uid));   //该用户的历史任务
        // 要从正在招募的任务重过滤掉该用户已经接受的任务
        taskList = filter(taskList, historyTasks);

        List<TaskVo> recommendList = new LinkedList<>();
        for (Task task : taskList) {
            TaskVo taskVo = new TaskVo(task);
            taskVo.setRatings(calRatings(task, historyTasks));  //依据用户做过任务与当前任务的匹配度来推荐
            recommendList.add(taskVo);
        }
        recommendList = recommendList.stream().sorted(Comparator.comparing(TaskVo::getRatings).reversed()).limit(N).collect(Collectors.toList());
        return recommendList;
    }

    @Override
    public ResultVo<TaskRecommendStrategyDTO> modify(HashMap<String, Double> newConfig, Integer N) {
        TaskRecommendStrategy.N = N;
        Set<String> set = newConfig.keySet();
        for (String key : set) {
            if (config.containsKey(key)) {
                config.put(key, newConfig.get(key));
            }
        }
        storeConfig(path);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "修改成功", new TaskRecommendStrategyDTO(this));
    }

    //基于历史任务的推荐方式
    private double calRatings(Task task, List<Task> historyTasks) {
        Set<String> set = config.keySet();
        double ratings = 0;
        try {
            for (Task historyTask : historyTasks) {
                for (String key : set) {
                    Field f1 = Task.class.getDeclaredField(key);
                    f1.setAccessible(true);
                    if (key.equals("difficulty")) {
                        if (f1.get(historyTask) == null || f1.get(task) == null || Math.max((Integer) f1.get(historyTask), (Integer) f1.get(task)) == 0) {
                            continue;
                        }
                        ratings += config.get(key) * Math.min((Integer) f1.get(historyTask), (Integer) f1.get(task)) / Math.max((Integer) f1.get(historyTask), (Integer) f1.get(task));
                    } else if (f1.get(historyTask).equals(f1.get(task))) {
                        ratings += config.get(key);
                    }
                }
            }
            return ratings;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void loadConfig(String path) {
        File file = new File(path);
        if (file.exists()) {     //配置文件存在，读取配置文件的内容
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                config = (HashMap<String, Double>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {     //配置文件不存在，遵从默认配置
            config = new HashMap<>();
            config.put("type", 1.0);      //任务类型
            config.put("deviceBrand", 1.0);   //设备品牌
            config.put("operatingSystem", 1.0);   //操作系统
            config.put("difficulty", 1.0);        //任务难度
        }
    }

    public void storeConfig(String path) {
        super.storeConfig();
        FileHelper.checkDirectoryPath(Constant.configDirectory);
        File file = new File(path);
        file.delete();
        if (!file.exists()) {
            try {
                file.createNewFile();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(config);
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Task> filter(List<Task> taskList, List<Task> historyTasks) {
        Set<Long> set = new HashSet<>();
        for (Task task : historyTasks) {
            set.add(task.getId());
        }
        return taskList.stream().filter(t -> !set.contains(t.getId())).collect(Collectors.toList());
    }
}
