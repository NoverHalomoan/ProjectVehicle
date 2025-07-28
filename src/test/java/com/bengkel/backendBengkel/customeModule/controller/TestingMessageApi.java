package com.bengkel.backendBengkel.customeModule.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class TestingMessageApi {

    @Autowired
    private MockMvc mockMvc;

    private String tokenUser;

    @Autowired
    private ObjectMapper objectMapper;

    private HttpStatus statusHttp;

    @BeforeEach
    void setUp() {

    }

    private void GetTokenLogin() {
        try {
            DTOLogin login = new DTOLogin("DonallAssadJaguar@gmail.com", "DonallAssad");
            mockMvc.perform(post("/api/auth/employee/loginEmployee")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(login)))
                    .andExpectAll(status().isOk())
                    .andDo(result -> {
                        String json = result.getResponse().getContentAsString();
                        WebResponse<ResponseDataEntity> resResult = objectMapper.readValue(json, new TypeReference<>() {
                        });
                        tokenUser = resResult.getData().getToken();
                    });
        } catch (Exception e) {
            log.error("Error get Token message " + e.getMessage());
        }
    }

    @Test
    @Disabled
    void TestInsertMessage() throws Exception {
        GetTokenLogin();
        mockMvc.perform(post("/api/v2/message/send")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenUser)
                .content(""))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    log.info("Testing " + result.getResponse().getStatus());
                });

    }
}
