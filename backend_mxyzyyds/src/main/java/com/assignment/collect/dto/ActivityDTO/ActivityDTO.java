package com.assignment.collect.dto.ActivityDTO;

import com.assignment.collect.vo.activeness.ActivenessVo;
import lombok.Data;
import lombok.NonNull;

@Data
public class ActivityDTO {
    @NonNull
    public ActivenessVo activeVo;
    @NonNull
    public Long score;
}
