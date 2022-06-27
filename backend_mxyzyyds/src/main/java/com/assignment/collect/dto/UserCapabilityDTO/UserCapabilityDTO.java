package com.assignment.collect.dto.UserCapabilityDTO;

import com.assignment.collect.vo.userCapability.UserCapabilityVo;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserCapabilityDTO {
    @NonNull
    public UserCapabilityVo userCapabilityVo;
    @NonNull
    public Long score;
}
