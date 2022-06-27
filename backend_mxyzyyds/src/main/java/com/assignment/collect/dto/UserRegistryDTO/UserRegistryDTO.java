package com.assignment.collect.dto.UserRegistryDTO;

import com.assignment.collect.vo.user.UserVo;
import com.assignment.collect.vo.userDevice.UserDeviceVo;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserRegistryDTO {
    @NonNull
    public UserDeviceVo[] userDeviceVos;
    @NonNull
    public UserVo userVo;
}
