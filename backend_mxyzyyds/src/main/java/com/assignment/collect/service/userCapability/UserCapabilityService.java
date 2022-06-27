package com.assignment.collect.service.userCapability;

import com.assignment.collect.dto.UserCapabilityDTO.UserCapabilityDTO;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.userCapability.TypeVo;
import com.assignment.collect.vo.userCapability.UserCapabilityVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserCapabilityService {

    ResultVo<UserCapabilityVo> getDetail(Integer uid);

    PageInfo<UserCapabilityDTO> getList(Integer uid, Integer page, Integer pageSize);

    ResultVo<List<UserCapabilityDTO>> updateAll(Integer uid);

    ResultVo<TypeVo> getTasksTypeByUserId(Integer uid);

    ResultVo<Double> getProbPref(Integer uid, String term);
}
