package com.assignment.collect.util;

import javax.servlet.http.HttpServletRequest;

public class AdminAuthentication {
    public static boolean isAdmin(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        if (subject.split("_")[1].equals("ADMINISTRATOR")) {
            return true;
        }
        return false;
    }
}
