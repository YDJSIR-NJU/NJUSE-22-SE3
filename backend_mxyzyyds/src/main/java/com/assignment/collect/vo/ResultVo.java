package com.assignment.collect.vo;

import lombok.Data;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 0:15
 */
@Data
public class ResultVo<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVo() {

    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
