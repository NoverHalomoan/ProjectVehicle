package com.bengkel.backendBengkel.customeModule.repository;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Department;
import com.bengkel.backendBengkel.employeeModule.model.DocumentFiles;
import com.bengkel.backendBengkel.employeeModule.service.FileServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
public class TestCostumeRepository {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private directRepositoryEmployee dRepositoryEmployee;

    @Autowired
    private directComponentEmployee dComponentEmployee;

    @Autowired
    private EnviromentGetData EnviromentGetData;

    @Autowired
    private FileServices fileServices;

    private String token;

    private String fileNamess;

    @BeforeEach
    public void setUP() throws Exception {
    }

    @Test
    @Disabled("Just Testing get Data")
    void TestCostumeRepository() {
        List<Department> department = dRepositoryEmployee.customeEmployeRepositor.findByDepartmentName("");
        System.out.println("Test " + department.get(0));
        System.out.println("Data enviroment " + EnviromentGetData.getIssuer());
    }

    @Test
    @Disabled("Disable for tetsting")
    void TestInsertData() {
        // ADMIN
        // PICUser
        ResponseEntity<?> response = dComponentEmployee.employeeService.insertDataRoleUser("PICUser");
        System.out.println("Data " + response.getBody());
    }

    // private String tryInsertFile() throws Exception {
    //     File fileTest = new File("src/test/resources/TestingFile.JPG");
    //     FileInputStream fis = new FileInputStream(fileTest);
    //     Response response = RestAssured.given()
    //             .header("Authorization", "Bearer " + token)
    //             .multiPart("file", fileTest)
    //             .when().post("/api/v1/file/insert");
    //     return response.getBody().jsonPath().get("data.fileName");
    // }
    @Test
    @Disabled("Skip Test Error")
    void TestingFileModul() throws Exception {
        File fileTest = new File("src/test/resources/TestingFile.JPG");
        FileInputStream fis = new FileInputStream(fileTest);

        // Optional<DocumentFiles> files = dRepositoryEmployee.fileRepository.findByFileName(filename);
        // dRepositoryEmployee.fileRepository.delete(files.get());
    }

    @Test
    @Disabled("Masih blm aktif")
    void TestingDeleteFileModul(String dataFilename) throws Exception {

    }
}
