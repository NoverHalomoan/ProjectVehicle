package com.bengkel.backendBengkel.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResolverComponent implements HandlerMethodArgumentResolver {

    private final directRepositoryEmployee dRepositoryEmployee;

    private final ObjectMapper objectMapper;

    public ResolverComponent(directRepositoryEmployee dRepositoryEmployee, ObjectMapper objectMapper) {
        this.dRepositoryEmployee = dRepositoryEmployee;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // TODO Auto-generated method stub
        return Employee.class.equals(parameter.getParameterType());
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        // TODO Auto-generated method stub
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = httpServletRequest.getHeader("APP-API-TOKEN");
        if (token == null) {
            throw new CostumeResponse(HttpStatus.UNAUTHORIZED, "Token has not expired");
        }

        return null;
    }

}
