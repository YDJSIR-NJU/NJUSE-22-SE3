package com.assignment.collect.dao.testRecord;

import com.assignment.collect.po.testRecord.TestRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.Date;
import java.util.List;

@Mapper
public interface TestRecordMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(TestRecord record);

    List<TestRecord> selectAll();

    TestRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    List<TestRecord> selectByTaskIdAndUid(Long taskid, Long uid);

    int updateByPrimaryKey(Long id, Long taskId, Long userId, Boolean finished, Date commitTime, Date finishTime);

    List<TestRecord> selectByUserId(Long uid);

    List<TestRecord> selectByTaskId(Long taskid);
}
