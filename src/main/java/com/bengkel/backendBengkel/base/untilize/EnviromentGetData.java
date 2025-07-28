package com.bengkel.backendBengkel.base.untilize;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EnviromentGetData {

    @Value("${jwt.JWT_SECRET}")
    private String Jwt_secrete;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${spring.mail.port}")
    private int emailPort;

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.username}")
    private String userNameEmail;

    @Value("${spring.mail.password}")
    private String passwordEmail;

    @Value("${jwt.JWT_ACTIVATION}")
    private String jwt_activationProfile;

    @Value("${jwt.JWT_UPDATE_PROFILE}")
    private String jwt_activeUpdateProfile;

    @Value("${app.baseUrl}")
    private String base_Url;

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String databaseNames;

    @Value("${spring.data.redis.host}")
    private String hostRedis;

    @Value("${spring.data.redis.port}")
    private int portRedis;
    //getter
}
