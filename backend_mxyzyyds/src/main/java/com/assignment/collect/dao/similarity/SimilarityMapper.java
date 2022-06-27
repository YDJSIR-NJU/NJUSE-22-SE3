package com.assignment.collect.dao.similarity;

import com.assignment.collect.po.similarity.Similarity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SimilarityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Similarity record);

    Similarity selectByPrimaryKey(Long id);

    List<Similarity> selectAll();

    int updateByPrimaryKey(Similarity record);

    List<Similarity> selectByReportId(Long reportid);
}
