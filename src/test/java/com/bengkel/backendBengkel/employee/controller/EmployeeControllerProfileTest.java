package com.bengkel.backendBengkel.employee.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOGetROLE;
import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.DTO.DTOUpdateProfile;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
public class EmployeeControllerProfileTest {

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @BeforeEach
    public void setUP() throws Exception {
        DTOLogin login = new DTOLogin("DonallAssadJaguar@gmail.com", "DonallAssad");
        ResponseEntity<?> responseToken = dComponentEmployee.employeeService.loginEmployeService(login);
        String jsonResponse = objectMapper.writeValueAsString(responseToken.getBody());
        WebResponse<ResponseDataEntity> GetResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        token = GetResponse.getData().getToken();
    }

    @Test
    @Disabled("Already testing")
    @Transactional
    void updateProfileEmployee() throws Exception {
        List<String> roleList = new ArrayList<>();
        roleList.add("PICUser");
        roleList.add("ADMIN");
        DTOGetROLE roless = new DTOGetROLE("12333", "Satuu");
        log.info("Data DTOGetRole " + objectMapper.writeValueAsString(roless));
        DTOUpdateProfile request = new DTOUpdateProfile("Donall Assad Jorgie Test", 8965798.0f, "DonallAssad", "DonallAssadJaguar@gmail.com", "", false, roleList);
        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(request)
                .put("/api/v1/employee/updateProfile");

        WebResponse<ResponseDataEntity> json = objectMapper.readValue(response.getBody().asInputStream(), new TypeReference<>() {
        });
        assertEquals(request.nameEmployee(), json.getData().getName());
    }

    private String GetTokenForActiveLogin() throws Exception {
        DTOLogin login = new DTOLogin("veribmtraining98@gmail.com", "TestingPassword");
        ResponseEntity<?> responseToken = dComponentEmployee.employeeService.loginEmployeService(login);
        String jsonResponse = objectMapper.writeValueAsString(responseToken.getBody());
        WebResponse<ResponseDataEntity> GetResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        token = GetResponse.getData().getToken();
        // Employee employee = dRepositoryEmployee.employeRepository.findByUsername("Noverhalomoan").orElseThrow(() -> new CostumeResponse(HttpStatus.BAD_REQUEST, "Error not found"));
        // employee.setIsactive(false);
        return token;
    }

    @Test
    @Disabled("Masih Dalam Pengembangan")
    void TestingDeleteEmployee() throws Exception {
        String tokenDeactive = GetTokenForActiveLogin();
    }

    @Test
    @Disabled("Masih Dalam Pengembangan")
    void TestingEmailEmployee() throws Exception {
        List<String> roleList = new ArrayList<>();
        roleList.add("PICUser");
        DTOUpdateProfile request = new DTOUpdateProfile("Donall Assad Jorgie", 8965798.0f, "DonallAssad", "DonallAssadJaguar@gmail.com", "", false, roleList);
    }
}
