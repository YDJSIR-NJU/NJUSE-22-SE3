package com.assignment.collect.service.activeness;

import com.assignment.collect.dto.ActivityDTO.ActivityDTO;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.activeness.ActivenessVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivenessService {
    ResultVo<List<ActivenessVo>> updateAllWk();

    ResultVo<ActivenessVo> getDetail(Integer uid);

    PageInfo<ActivityDTO> getList(Integer uid, Integer page, Integer pageSize);

    ResultVo<List<ActivenessVo>> updateAllMon();
}
