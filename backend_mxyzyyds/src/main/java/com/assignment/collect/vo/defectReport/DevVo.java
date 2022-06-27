package com.assignment.collect.vo.defectReport;

import lombok.Data;
import lombok.NonNull;

@Data
public class DevVo {
    @NonNull
    public Long WINDOWS;
    @NonNull
    public Long LINUX;
    @NonNull
    public Long MACOS;
    @NonNull
    public Long ANDROID;
    @NonNull
    public Long IOS;
    @NonNull
    public Long IPADOS;
    @NonNull
    public Long OTHER;
}
