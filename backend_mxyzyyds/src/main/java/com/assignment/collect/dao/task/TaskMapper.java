package com.assignment.collect.dao.task;

import com.assignment.collect.po.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Task record);

    Task selectByPrimaryKey(Long id);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);

    List<Task> selectByUid(Long uid);

    List<Task> selectProcessingByUid(Long uid);

    List<Task> selectHistoryByUid(Long uid);

    List<Task> todoList();

}
