package com.bengkel.backendBengkel.base.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomeDeniedHandler implements AccessDeniedHandler {

    // @Override
    // public void commence(HttpServletRequest request, HttpServletResponse response,
    //         AuthenticationException authException) throws IOException, ServletException {
    //     // TODO Auto-generated method stub
    //     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    //     response.setContentType("application/json");
    //     Map<String, Object> responseBody = new HashMap<>();
    //     responseBody.put("status", 403);
    //     responseBody.put("error", "FORBIDDEN");
    //     responseBody.put("message", "You are not allowed to access this endpoint");
    //     responseBody.put("path", request.getRequestURI());
    //     new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    // }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 403);
        responseBody.put("error", "FORBIDDEN");
        responseBody.put("message", "You are not allowed to access this endpoint");
        // responseBody.put("path", request.getRequestURI());

        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

}
