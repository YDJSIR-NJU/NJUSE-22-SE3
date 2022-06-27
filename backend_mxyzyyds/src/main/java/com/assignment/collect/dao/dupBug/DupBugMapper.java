package com.assignment.collect.dao.dupBug;

import com.assignment.collect.po.dupBug.DupBug;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DupBugMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DupBug record);

    DupBug selectByPrimaryKey(Integer id);

    List<DupBug> selectAll();

    int updateByPrimaryKey(DupBug record);

    DupBug selectByReportid(Integer reportId);
}