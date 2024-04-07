package com.atibo.backendspring.common;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import io.jsonwebtoken.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, java.io.IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        System.out.println("접근권한x(로그인안함)");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"detail\": \"You do not have permission to perform this action.\"}");
        response.getWriter().flush();
    }
}