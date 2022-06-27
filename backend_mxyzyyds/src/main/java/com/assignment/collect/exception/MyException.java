package com.assignment.collect.exception;

import com.assignment.collect.enums.ExceptionType;
import lombok.AllArgsConstructor;

/**
 * @Author: XiaYu
 * @Date 2022/2/25 21:13
 */
@AllArgsConstructor()
public class MyException extends RuntimeException {
    private final ExceptionType type;
    private final String msg;

    public String getMsg() {
        return msg;
    }
}
