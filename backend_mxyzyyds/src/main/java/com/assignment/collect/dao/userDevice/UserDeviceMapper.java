package com.assignment.collect.dao.userDevice;

import com.assignment.collect.po.userDevice.UserDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UserDeviceMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserDevice record);

    UserDevice selectByPrimaryKey(Long id);

    List<UserDevice> selectAll();

    int updateByPrimaryKey(UserDevice record);

    List<UserDevice> selectByUserId(Integer userId);
}
