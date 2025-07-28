package com.bengkel.backendBengkel.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@Slf4j
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigurationBased {

    @LocalServerPort
    private int port;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        System.out.println("Port "   + port);
        RestAssured.port = port;
    }

    private String LoginForALL() throws Exception {
        DTOLogin login = new DTOLogin("DonallAssad@gmail.com", "DonallAssad");
        ResponseEntity<?> responseToken = dComponentEmployee.employeeService.loginEmployeService(login);
        String jsonResponse = objectMapper.writeValueAsString(responseToken.getBody());
        WebResponse<ResponseDataEntity> GetResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        String token = GetResponse.getData().getToken();
        return token;
    }

    protected RequestSpecification RequestWithAuth() throws Exception {
        String token = LoginForALL();
        return RestAssured.given().header("Authorization", "Bearer " + token);
    }
}
