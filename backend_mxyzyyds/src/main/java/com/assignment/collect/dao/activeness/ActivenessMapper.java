package com.assignment.collect.dao.activeness;

import com.assignment.collect.po.activeness.Activeness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ActivenessMapper {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Activeness record);

    Activeness selectByPrimaryKey(Long id);

    List<Activeness> selectAll();

    int updateByPrimaryKey(Activeness record);

    Activeness selectByUserID(Long uid);
}