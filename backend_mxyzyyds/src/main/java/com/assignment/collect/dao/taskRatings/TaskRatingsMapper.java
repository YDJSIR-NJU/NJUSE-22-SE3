package com.assignment.collect.dao.taskRatings;

import com.assignment.collect.po.taskRatings.TaskRatings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskRatingsMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("taskId") Long taskId);

    int insert(TaskRatings record);

    TaskRatings selectByPrimaryKey(@Param("userId") Long userId, @Param("taskId") Long taskId);

    List<TaskRatings> selectAll();

    int updateByPrimaryKey(TaskRatings record);

    List<TaskRatings> selectByUid(@Param("userId") Long userId);

    List<TaskRatings> selectByTaskId(@Param("taskId") Long taskId);
}
