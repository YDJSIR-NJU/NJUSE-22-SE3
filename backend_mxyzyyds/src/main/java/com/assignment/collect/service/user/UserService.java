package com.assignment.collect.service.user;

import com.assignment.collect.dto.UserRegistryDTO.UserRegistryDTO;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.user.UserVo;
import com.assignment.collect.vo.userDevice.UserDeviceVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 0:04
 */
public interface UserService {
    //用户注册
    ResultVo<UserVo> userRegister(UserRegistryDTO userRegistryDTO);

    //用户登录
    ResultVo<UserVo> userLogin(String email, String password);


    ResultVo<UserVo> getUserVo(Integer uid);

    ResultVo<UserDeviceVo> addUserDevices(UserDeviceVo userDeviceVo, Integer uid);

    ResultVo<UserDeviceVo> deleteUserDevices(Long userDeviceRecordId, Integer uid);

    ResultVo<UserDeviceVo> updateUserDevices(UserDeviceVo userDeviceVo, Integer uid);

    PageInfo<UserDeviceVo> getUserDevices(Integer currPage, Integer pageSize, Integer uid);
}
