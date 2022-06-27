package com.assignment.collect.vo.userDevice;

import com.assignment.collect.po.userDevice.UserDevice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDeviceVo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String operatingSystem;
    private String deviceBrand;

    public UserDeviceVo(UserDevice userDevice) {
        this.id = userDevice.getId();
        this.userId = userDevice.getUserId();
        this.operatingSystem = userDevice.getOperatingSystem();
        this.deviceBrand = userDevice.getDeviceBrand();
    }
}
