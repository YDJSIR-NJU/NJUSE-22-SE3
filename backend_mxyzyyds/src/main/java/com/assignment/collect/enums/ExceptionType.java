package com.assignment.collect.enums;

import org.springframework.http.HttpStatus;

/**
 * @Author: XiaYu
 * @Date 2022/2/25 21:14
 */
public enum ExceptionType {
    PARAM_ERROR(HttpStatus.BAD_REQUEST), UNAUTHORIZED(HttpStatus.UNAUTHORIZED), NOT_FOUND(HttpStatus.NOT_FOUND), SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;

    ExceptionType(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
