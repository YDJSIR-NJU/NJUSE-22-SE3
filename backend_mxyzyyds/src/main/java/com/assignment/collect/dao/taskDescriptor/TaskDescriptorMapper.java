package com.assignment.collect.dao.taskDescriptor;

import com.assignment.collect.po.taskDescriptor.TaskDescriptor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDescriptorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskDescriptor record);

    TaskDescriptor selectByPrimaryKey(Long id);

    List<TaskDescriptor> selectAll();

    int updateByPrimaryKey(TaskDescriptor record);

    String selectByTaskid(Integer taskId);
}