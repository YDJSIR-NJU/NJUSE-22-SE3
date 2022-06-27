package com.assignment.collect.po.userDevice;

import com.assignment.collect.vo.userDevice.UserDeviceVo;

import java.io.Serializable;

public class UserDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String operatingSystem;
    private String deviceBrand;

    public UserDevice() {

    }

    public UserDevice(UserDeviceVo userDeviceVo) {
        this.id = userDeviceVo.getId();
        this.userId = userDeviceVo.getUserId();
        this.operatingSystem = userDeviceVo.getOperatingSystem();
        this.deviceBrand = userDeviceVo.getDeviceBrand();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }
}
