package com.bengkel.backendBengkel.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOEmployee;
import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createNewEmployeeSuccess() throws Exception {
        DTOEmployee dtoemployee = new DTOEmployee("Nover HalomoanTest", "NoverhalomoanTest", 23000000f, "", "TestingPassword", "veribmtraining98Test@gmail.com", "PICUser");

        mockMvc.perform(post("/api/auth/employee/createEmployee")
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dtoemployee)))
                .andExpectAll(status().isOk()).andDo(result -> {
            WebResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            String res = objectMapper.writeValueAsString(response);
            log.info("Data response " + res);

        });
    }

    //this error becuase department not found
    @Test
    void createNewEmployeeFail() throws Exception {
        DTOEmployee dtoemployee = new DTOEmployee("Kasut", "username", 23000000, "Software Development", "TestingPassword", "email@gmail.com", "ADMIN");

        mockMvc.perform(post("/api/auth/employee/createEmployee")
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dtoemployee)))
                .andExpectAll(status().isNotFound()).andDo(result -> {
            log.info("Data response " + result.getResponse().getContentAsString());
        });
    }

    //test login for employee
    @Test
    void loginEmployeServiceSuccess() throws Exception {
        DTOLogin login = new DTOLogin("DonallAssadJaguar@gmail.com", "DonallAssad");
        mockMvc.perform(post("/api/auth/employee/loginEmployee")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(login)))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    WebResponse<ResponseDataEntity> resResult = objectMapper.readValue(json, new TypeReference<>() {
                    });
                    assertEquals(result.getResponse().getStatus(), 200);
                    assertEquals(resResult.getErrors(), null);
                });
    }
}
