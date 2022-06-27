package com.assignment.collect.vo.python;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimilarityCalcRetVO {
    private Integer[] dest_id;
    private Double[] score;
    private Integer src_id;
}
