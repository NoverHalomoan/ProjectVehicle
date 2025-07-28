package com.bengkel.backendBengkel.employeeModule.service.implService;

import org.springframework.http.ResponseEntity;

import com.bengkel.backendBengkel.base.DTO.DTOEmployee;
import com.bengkel.backendBengkel.base.DTO.DTOUpdateProfile;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

public interface InFaceEmployeeMethod {

    ResponseEntity<?> createNewEmployee(DTOEmployee request);

    ResponseEntity<?> updateEmployee(Employee usrEmployee, DTOUpdateProfile request);

    ResponseEntity<?> deleteEmployee(Employee usrEmployee);

    ResponseEntity<?> reactiveAccount(String emailUser) throws Exception;
}
