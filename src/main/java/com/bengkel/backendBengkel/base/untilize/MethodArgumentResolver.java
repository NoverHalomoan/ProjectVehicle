package com.bengkel.backendBengkel.base.untilize;

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
import com.bengkel.backendBengkel.base.security.CustomUserDetailsService;
import com.bengkel.backendBengkel.base.security.JwtUtilFilter;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtUtilFilter jwtUtilFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
        String AuthorizationToken = httpServletRequest.getHeader("Authorization");
        String jwt = AuthorizationToken.substring(7);
        if (jwt == null) {
            throw new CostumeResponse(HttpStatus.UNAUTHORIZED, "Access is denied do to invalid credential");
        }
        String idUser = jwtUtilFilter.extractUsername(jwt, "login");
        log.info("Handler method argument resolver done");
        Employee employee = customUserDetailsService.loadGetDataEmployee(idUser);
        if (employee.getIsactive() == false) {
            throw new CostumeResponse(HttpStatus.UNAUTHORIZED, "Access is denied, please activation user again");
        }
        return employee;
    }

}
