package com.bengkel.backendBengkel.employeeModule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final directComponentEmployee dComponentEmployee;

    public FileController(directComponentEmployee dComponentEmployee) {
        this.dComponentEmployee = dComponentEmployee;
    }

    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> InsertFileDocument(Employee employee, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Start processing controller for insert file");
        if (file.isEmpty()) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "File is empty");
        }
        return dComponentEmployee.fileServices.insertFileDocument(employee, file);
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findFileBasedOnFileName(Employee employee, @RequestParam(value = "fileName") String fileName) {
        log.info("Start controller get document");
        Map<String, String> fileMapping = new HashMap<>();
        fileMapping.put("fileName", fileName);
        return dComponentEmployee.fileServices.searchFileDocument(fileMapping);
    }

    @DeleteMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFileDocument(Employee employee, @RequestParam String fileName) {
        log.info("Start controller for insert data file");
        return dComponentEmployee.fileServices.deleteFileDocument(employee, fileName);
    }
}
