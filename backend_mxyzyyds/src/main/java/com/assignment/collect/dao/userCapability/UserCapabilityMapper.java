package com.assignment.collect.dao.userCapability;

import com.assignment.collect.po.userCapability.UserCapability;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UserCapabilityMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserCapability record);

    UserCapability selectByPrimaryKey(Long id);

    List<UserCapability> selectAll();

    int updateByPrimaryKey(UserCapability record);

    UserCapability selectByUserId(Long uid);
}