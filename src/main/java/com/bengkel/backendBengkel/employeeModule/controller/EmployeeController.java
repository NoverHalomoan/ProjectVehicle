package com.bengkel.backendBengkel.employeeModule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bengkel.backendBengkel.base.DTO.DTOUpdateProfile;
import com.bengkel.backendBengkel.base.Services.EmailServiceSend;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

@Controller
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final directComponentEmployee directService;

    private final EmailServiceSend emailServiceSend;

    public EmployeeController(directComponentEmployee directService, EmailServiceSend emailServiceSend) {
        this.directService = directService;
        this.emailServiceSend = emailServiceSend;
    }

    @PutMapping(path = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(Employee employee, @RequestBody DTOUpdateProfile request) {
        return directService.employeeService.updateEmployee(employee, request);
    }

    @GetMapping(path = "/updateProfile")// code ini ngak kepakai hanya untuk test email
    public ResponseEntity<?> getTestingEmail(@RequestBody DTOUpdateProfile request) throws Exception {
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("name", "Nover Halomoan");
        contentMap.put("activationLink", "oke/okkkkk");
        emailServiceSend.sendEmail("veribmtraining98@gmail.com", "Body Testing Baru", contentMap, "emailTemplate/email-confirmation");
        return new ResponseEntity<>("oke", HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deactivateAccountEmployee(Employee employee) {
        return directService.employeeService.deleteEmployee(employee);
    }

    @PostMapping(path = "/role")
    public ResponseEntity<?> insertRoleUser(@RequestParam String role) {
        return directService.employeeService.insertDataRoleUser(role);
    }

}
