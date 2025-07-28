package com.bengkel.backendBengkel.employeeModule.service.implService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bengkel.backendBengkel.employeeModule.model.Employee;

public interface InterfaceMethodFile {

    ResponseEntity<?> insertFileDocument(Employee employee, MultipartFile file);

    ResponseEntity<?> deleteFileDocument(Employee employee, String fileName);

    ResponseEntity<?> searchFileDocument(Map<String, String> searchFile);
}
