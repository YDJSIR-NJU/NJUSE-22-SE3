package com.assignment.collect.config;

import com.assignment.collect.util.JWTUtils;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: XiaYu
 * @Date 2022/3/4 16:46
 */
public class JWTInterceptor implements HandlerInterceptor {

//    public static boolean NO_LOGIN = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (NO_LOGIN){
//            return true;
//        }

        String token = request.getHeader(JWTUtils.header);
        if (token == null || token.equals(""))
            throw new JWTDecodeException("未登录！");
        String subject = JWTUtils.verify(token);//如果token不正确这里会抛出异常
        if (JWTUtils.isNeedUpdate(token)) {
            String newToken = JWTUtils.getToken(subject);
            response.setHeader(JWTUtils.header, newToken);
        }
        return true;
    }
}
