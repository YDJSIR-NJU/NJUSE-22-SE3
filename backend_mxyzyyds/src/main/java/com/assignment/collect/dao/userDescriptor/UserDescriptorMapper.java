package com.assignment.collect.dao.userDescriptor;

import com.assignment.collect.po.userDescriptor.UserDescriptor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDescriptorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDescriptor record);

    UserDescriptor selectByPrimaryKey(Long id);

    List<UserDescriptor> selectAll();

    int updateByPrimaryKey(UserDescriptor record);

    UserDescriptor selectByUid(Integer userId);
}