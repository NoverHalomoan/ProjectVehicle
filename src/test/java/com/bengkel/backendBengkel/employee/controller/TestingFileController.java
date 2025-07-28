package com.bengkel.backendBengkel.employee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.employeeModule.repository.EmployeRepository;
import com.bengkel.backendBengkel.employeeModule.service.FileServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestingFileController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private String token;

    private String filename;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private FileServices fileService;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @BeforeEach
    void setUp() throws Exception {
        DTOLogin login = new DTOLogin("DonallAssadJaguar@gmail.com", "DonallAssad");
        ResponseEntity<?> responseToken = dComponentEmployee.employeeService.loginEmployeService(login);
        String jsonResponse = objectMapper.writeValueAsString(responseToken.getBody());
        WebResponse<ResponseDataEntity> GetResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        token = GetResponse.getData().getToken();
    }

    @Test
    void TestingInsertFile() throws Exception {
        File fileTest = new File("src/test/resources/TestingFile.JPG");
        FileInputStream fis = new FileInputStream(fileTest);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                fileTest.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fis
        );

        mockMvc.perform(multipart("/api/v1/file/insert")
                .file(multipartFile)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<ResponseDataEntity> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertEquals("Success save file", response.getMessage());
                });
    }

    @Test
    void TestingGetDataFile() throws Exception {
        mockMvc.perform(get("/api/v1/file/")
                .header("Authorization", "Bearer " + token)
                .param("fileName", "File_15801605842025-07-21T19:59:19.141827900.jpg"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    try {
                        WebResponse<List<ResponseDataEntity>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<ResponseDataEntity>>>() {

                        });
                        assertEquals("Success get Document files", response.getMessage());
                        if (!response.getData().isEmpty()) {
                            String FileGet = String.join(",", response.getData().stream().map(req -> req.getFileName()).collect(Collectors.toList()));
                            log.info("File Data " + FileGet);
                        }
                    } catch (MismatchedInputException e) {
                        log.debug("Error convert data object");
                    }
                });
    }

    private String InsertDataFileToTestSystem() throws Exception {
        //DonallAssadJaguar@gmail.com
        Employee employee = employeRepository.findByEmail("DonallAssadJaguar@gmail.com").get();
        File fileTest = new File("src/test/resources/TestingFile.JPG");
        FileInputStream fis = new FileInputStream(fileTest);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                fileTest.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fis
        );
        ResponseEntity<?> response = fileService.insertFileDocument(employee, multipartFile);
        String jsonData = objectMapper.writeValueAsString(response.getBody());
        WebResponse<ResponseDataEntity> readResponse = objectMapper.readValue(jsonData, new TypeReference<>() {
        });
        return readResponse.getData().getFileName();
    }

    @Test
    void TestingdeleteFile() throws Exception {
        filename = InsertDataFileToTestSystem();
        // log.info("Data file " + filename);
        mockMvc.perform(delete("/api/v1/file/")
                .header("Authorization", "Bearer " + token)
                .param("fileName", filename))
                .andExpect(status().isOk())
                .andDo(result -> {
                    WebResponse<?> readResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    log.info("Data Response " + readResponse.getMessage());
                });

    }
}
