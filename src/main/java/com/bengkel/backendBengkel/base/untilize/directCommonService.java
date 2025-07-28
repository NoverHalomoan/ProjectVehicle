package com.bengkel.backendBengkel.base.untilize;

import org.springframework.stereotype.Component;

import com.bengkel.backendBengkel.base.Services.ServiceRedisData;
import com.bengkel.backendBengkel.base.security.JwtUtilFilter;

@Component
public class directCommonService {

    public JwtUtilFilter jwtUtilFilter;

    public final ServiceRedisData serviceRedisData;

    public directCommonService(JwtUtilFilter jwtUtilFilter, ServiceRedisData serviceRedisData) {
        this.jwtUtilFilter = jwtUtilFilter;
        this.serviceRedisData = serviceRedisData;
    }

}
