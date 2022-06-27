package com.assignment.collect.serviceimpl.userCapabilityServiceImpl;

import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.dto.UserCapabilityDTO.UserCapabilityDTO;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userCapability.UserCapability;
import com.assignment.collect.service.userCapability.UserCapabilityService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.userCapability.TypeVo;
import com.assignment.collect.vo.userCapability.UserCapabilityVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.collect.enums.UserRole.WORKER;

@Service
public class UserCapabilityServiceImpl implements UserCapabilityService {
    @Autowired
    UserCapabilityMapper userCapabilityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TestRecordMapper testRecordMapper;
    @Autowired
    TaskMapper taskMapper;

    @Override
    public ResultVo<UserCapabilityVo> getDetail(Integer uid) {
        /**
         * TODO 异常状况处理
         */
        UserCapability userCapability = userCapabilityMapper.selectByUserId(Long.valueOf(uid));
        if (userCapability == null) {
            userCapability = new UserCapability();
            userCapability.setUid(Long.valueOf(uid));
            userCapabilityMapper.insert(userCapability);
            userCapability = userCapabilityMapper.selectByUserId(Long.valueOf(uid));
        }

        return new ResultVo<>(Constant.REQUEST_SUCCESS, "获取用户能力信息成功", new UserCapabilityVo(userCapability));
    }

    @Override
    public PageInfo<UserCapabilityDTO> getList(Integer uid, Integer page, Integer pageSize) {
        List<UserCapability> pre = userCapabilityMapper.selectAll();
        List<UserCapabilityDTO> after = new ArrayList<>();
        for (UserCapability uc : pre) {
            User user = userMapper.selectByPrimaryKey(uc.getUid());
            if (user.getUserRole().equals(WORKER)) {
                /**
                 * TODO 需要新的公式计算评分
                 */
                Long score = uc.getTotalBugNums();
                UserCapabilityDTO userCapabilityDTO = new UserCapabilityDTO(new UserCapabilityVo(uc), score);
                after.add(userCapabilityDTO);

            }
        }
        if (page == null || page < 1) page = 1;  //默认为第一页
        PageHelper.startPage(page, pageSize);
        PageInfo<UserCapabilityDTO> po = new PageInfo<>(after);
        return po;
    }

    @Override
    public ResultVo<List<UserCapabilityDTO>> updateAll(Integer uid) {

        return null;
    }

    @Override
    public ResultVo<TypeVo> getTasksTypeByUserId(Integer uid) {
        int FUN = 0, PER = 0;


        List<TestRecord> testRecordList = testRecordMapper.selectByUserId(uid.longValue());
        //获取用户测试记录
        if (testRecordList != null && testRecordList.size() > 0) {
            for (TestRecord testRecord : testRecordList) {
                Task task = taskMapper.selectByPrimaryKey(testRecord.getTaskId());
                if (task != null) {
                    if (task.getType().equals("FUNCTION")) {
                        FUN++;
                    } else if (task.getType().equals("PERFORMANCE")) {
                        PER++;
                    }
                }
            }
            TypeVo typeVo = new TypeVo(Long.valueOf(FUN), Long.valueOf(PER));

            return new ResultVo<>(Constant.REQUEST_SUCCESS, "获取用户任务类型数成功！", typeVo);
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "用户还没有参与过测试任务！");
    }

    @Override
    public ResultVo<Double> getProbPref(Integer uid, String term) {
        return null;
    }
}
