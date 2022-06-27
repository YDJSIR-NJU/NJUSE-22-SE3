package com.assignment.collect.serviceimpl.activeness;

import com.assignment.collect.dao.activeness.ActivenessMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dto.ActivityDTO.ActivityDTO;
import com.assignment.collect.po.activeness.Activeness;
import com.assignment.collect.po.user.User;
import com.assignment.collect.service.activeness.ActivenessService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.activeness.ActivenessVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.collect.enums.UserRole.WORKER;

@Service
public class ActivenessServiceImpl implements ActivenessService {
    @Autowired
    ActivenessCalAsync activenessCalAsync;
    @Autowired
    ActivenessMapper activenessMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 2)
    public ResultVo<List<ActivenessVo>> updateAllWk() {
        List<ActivenessVo> list = new ArrayList<>();
        activenessCalAsync.updateAllWk(-7);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "更新用户活跃度成功", list);
    }

    @Override
    public ResultVo<ActivenessVo> getDetail(Integer uid) {
        /**
         * 需要过滤掉发包方和管理员的数据
         */
        Activeness activeness = activenessMapper.selectByUserID(uid.longValue());
        if (activeness == null) {
            activeness = new Activeness();
            activeness.setUid(uid.longValue());
            activenessMapper.insert(activeness);
        }
        activeness = activenessMapper.selectByUserID(uid.longValue());
        /**
         * TODO 异常情况处理
         */
        return new ResultVo<ActivenessVo>(Constant.REQUEST_SUCCESS, "获取活跃度信息成功", new ActivenessVo(activeness));
    }

    @Override
    public PageInfo<ActivityDTO> getList(Integer uid, Integer currPage, Integer pageSize) {
        /**
         * TODO 异常状况处理 test
         *
         */
        List<Activeness> pre = activenessMapper.selectAll();
        List<ActivityDTO> aft = new ArrayList<>();
        for (Activeness ac : pre) {
            User user = userMapper.selectByPrimaryKey(ac.getUid());
            if (user.getUserRole().equals(WORKER)) {
                /**
                 * TODO 需要在此处对活跃度进行评级评分根据评级信息展示
                 */
                Long score = ac.getNumbugsWeek() + ac.getNumreportsWeek() + ac.getNumtasksWeek();
                ActivityDTO adto = new ActivityDTO(new ActivenessVo(ac), score);
                aft.add(adto);
            }
        }


        if (currPage == null || currPage < 1) currPage = 1;  //默认为第一页
        PageHelper.startPage(currPage, pageSize);
        PageInfo<ActivityDTO> po = new PageInfo<>(aft);
        return po;
    }

    @Override
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 2)
    public ResultVo<List<ActivenessVo>> updateAllMon() {
        List<ActivenessVo> list = new ArrayList<>();
        activenessCalAsync.updateAllMon(-90);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "更新用户活跃度成功", list);
    }

    PageInfo<ActivenessVo> getActivenessVOPageInfo(Integer uid, PageInfo<Activeness> po) {
        return PageInfoUtil.convert(po, ActivenessVo.class);
    }


}
