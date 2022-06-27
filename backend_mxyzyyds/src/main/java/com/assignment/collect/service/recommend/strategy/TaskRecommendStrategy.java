package com.assignment.collect.service.recommend.strategy;

import com.assignment.collect.dto.TaskRecommendStrategyDTO.TaskRecommendStrategyDTO;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.FileHelper;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import lombok.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 22:59
 */
@Data
public abstract class TaskRecommendStrategy {
    public static Integer N = 10;  //推荐的任务数

    static {
        File file = new File(Constant.configDirectory + File.separator + Constant.NConfig);
        if (file.exists()) {     //配置文件存在，读取配置文件的内容
            try {
                FileReader fileReader = new FileReader(file);
                N = fileReader.read();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer strategyId;
    public Map<String, Double> config;
    public String description;
    public boolean inUse = false;

    public abstract List<TaskVo> recommend(Integer uid);

    public abstract ResultVo<TaskRecommendStrategyDTO> modify(HashMap<String, Double> newConfig, Integer N);

    public void storeConfig() {
        FileHelper.checkDirectoryPath(Constant.configDirectory);
        File file = new File(Constant.configDirectory + File.separator + Constant.NConfig);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(N);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
