package com.assignment.collect.dao.message;

import com.assignment.collect.po.message.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    // 其他地方可以调用来发布消息
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);

    List<Message> selectByUserId(Long userId);

    List<Message> selectNewByUserId(Long userId);
}
