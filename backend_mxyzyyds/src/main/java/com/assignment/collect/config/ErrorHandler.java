package com.assignment.collect.config;


import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandler {     //全局异常处理

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<MethodArgumentNotValidException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResultVo<>(Constant.REQUEST_ERROR, e.getMessage());
    }


    /**
     * 处理token异常
     */
    @ExceptionHandler(value = {SignatureVerificationException.class, AlgorithmMismatchException.class, JWTDecodeException.class})
    public ResultVo<String> tokenErrorException() {
        ResultVo<String> result = new ResultVo<>();
        result.setCode(Constant.TOKEN_ERROR_EXCEPTION);
        result.setMsg("请先登录！");
        return result;
    }

    /**
     * 处理token异常
     */
    @ExceptionHandler(value = TokenExpiredException.class)
    public ResultVo<String> tokenExpiredException() {
        ResultVo<String> result = new ResultVo<>();
        result.setCode(Constant.TOKEN_EXPIRED_EXCEPTION);
        result.setMsg("登录过期，请重新登录！");
        return result;
    }

    /**
     * 处理所有RuntimeException异常
     */
    @ExceptionHandler({RuntimeException.class})
    public ResultVo<String> allException(RuntimeException e) {
        ResultVo<String> result = new ResultVo<>();
        result.setCode(Constant.DEFAULT_EXCEPTION);
        result.setMsg(e.getMessage());
        e.printStackTrace();
        return result;
    }

    /**
     * 处理所有Exception异常
     */
    @ExceptionHandler({Exception.class})
    public ResultVo<String> allException(Exception e) {
        ResultVo<String> result = new ResultVo<>();
        result.setCode(Constant.DEFAULT_EXCEPTION);
        result.setMsg(e.getMessage());
        e.printStackTrace();
        return result;
    }

}
