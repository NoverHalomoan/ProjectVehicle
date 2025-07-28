package com.bengkel.backendBengkel.employeeModule.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bengkel.backendBengkel.base.DTO.DTOEmployee;
import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.untilize.directComponentEmployee;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth/employee")
public class EmployeeAuthController {

    private final directComponentEmployee directService;

    @PostMapping(path = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewEmployee(@RequestBody DTOEmployee request) {
        return directService.employeeService.createNewEmployee(request);
    }

    @PostMapping(path = "/loginEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginEmployee(@RequestBody DTOLogin request) {
        return directService.employeeService.loginEmployeService(request);
    }

    @GetMapping(path = "/en/register/confirm")
    public ResponseEntity<?> ActivationProfile(@RequestParam(required = true) String confirm) {
        return directService.employeeService.activasiAccountRegistrasi(confirm);
    }

    @PostMapping(path = "/updateProfile")
    public ResponseEntity<?> reactiveControllerUser(@RequestParam(value = "email", required = true) String email) {
        return directService.employeeService.reactiveAccount(email);
    }

    @GetMapping(path = "/en/profile/confirm")
    public ResponseEntity<?> ProfileDeactivateAccount(@RequestParam(required = true) String confim) {
        return directService.employeeService.reActiveProfileAccount(confim);
    }

}
