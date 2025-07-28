package com.bengkel.backendBengkel.employeeModule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOEmployee;
import com.bengkel.backendBengkel.base.DTO.DTOLogin;
import com.bengkel.backendBengkel.base.DTO.DTOUpdateProfile;
import com.bengkel.backendBengkel.base.Services.EmailServiceSend;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.security.CustomUserDetailsService;
import com.bengkel.backendBengkel.base.security.EncriptandDecriptPassword;
import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;
import com.bengkel.backendBengkel.base.untilize.directCommonService;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Department;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.employeeModule.model.RoleUser;
import com.bengkel.backendBengkel.employeeModule.service.implService.ImplementServiceEmployee;
import com.bengkel.backendBengkel.employeeModule.service.implService.InFaceEmployeeMethod;

@Service
public class EmployeeService extends ImplementServiceEmployee implements InFaceEmployeeMethod {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public EmployeeService(directRepositoryEmployee dirctRepoEmployee, AuthenticationManager authenticationManager,
            CustomUserDetailsService customeUserDetailService, EmailServiceSend emailServiceSend,
            directCommonService directComService, EnviromentGetData enviromentGetData) {
        super(dirctRepoEmployee, authenticationManager, customeUserDetailService, emailServiceSend, directComService,
                enviromentGetData);
    }

    @Transactional
    public Employee getDetailEmployee(String idUser) {
        return dirctRepoEmployee.employeRepository.findById(idUser).orElseThrow(()
                -> new CostumeResponse(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public ResponseEntity<?> createNewEmployee(DTOEmployee request) {
        log.info("Start Processing Create new Employee " + request.nameEmployee());
        try {
            List<Department> department = dirctRepoEmployee.customeEmployeRepositor.findByDepartmentName(request.departmentName());

            if (department.isEmpty()) {
                log.warn("Invalid Department to Create new Employee " + request.nameEmployee());
                throw new CostumeResponse(HttpStatus.NOT_FOUND, "Departement not found");
            }
            if (request.username().contains(" ")) {
                log.warn("Invalid Username to Create new Employee " + request.nameEmployee());
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Username is not valid");
            }
            if (request.email().contains(" ") || !request.email().contains("@")) {
                log.warn("Invalid Email to Create new Employee " + request.nameEmployee());
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Email is not valid");
            }
            if (checkregistrationAccount(request.username(), request.email())) {
                log.warn("Invalid request to Create new Employee " + request.nameEmployee());
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Data Request invalid");
            }
            float salary = (float) (request.salary() == 0.0f ? 0.0 : request.salary());
            Employee employee = new Employee(department.get(0), request.nameEmployee(), salary, EncriptandDecriptPassword.encriptPassword(request.password()), request.username(), request.email(), request.role());
            Employee saveEmployee = dirctRepoEmployee.employeRepository.save(employee);
            String pathLink = enviromentGetData.getBase_Url() + "/api/auth/employee/en/register/confirm?confirm=" + directComService.jwtUtilFilter.generatedTokenActivation(employee.getEmail());
            Map<String, String> MapContext = new HashMap<>();
            MapContext.put("name", saveEmployee.getName());
            MapContext.put("activationLink", pathLink);
            emailServiceSend.sendEmail(saveEmployee.getEmail(), "Activation Account", MapContext, "emailTemplate/email-confirmation");
            log.info("Success create new employee {}", saveEmployee.getName());
            return new ResponseEntity<>(WebResponse.builder().data(employee.ResponseEmployee(saveEmployee.getName(), null, null, saveEmployee.getSalary())).StatusCode(HttpStatus.OK.value()).message("Success create new user").build(), HttpStatus.OK);
        } catch (CostumeResponse a) {
            log.debug("Error Costume while create new employee " + a.getMessage());
            throw a;
        } catch (Exception e) {
            log.debug("Error while create new employee " + e.getMessage());
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Employee usrEmployee) {
        // TODO Auto-generated method stub
        usrEmployee.setIsactive(false);
        dirctRepoEmployee.employeRepository.save(usrEmployee);
        return new ResponseEntity<>("Success deactivation account " + usrEmployee.getUsername(), HttpStatus.OK);
    }

    public ResponseEntity<?> activasiAccountRegistrasi(String codeActivasi) {
        log.info("Start activation profile " + codeActivasi);
        Map<String, Boolean> responseActive = directComService.jwtUtilFilter.validationActivationUser(codeActivasi);
        String emailUser = responseActive.keySet().stream().toList().get(0);
        log.info("check activation " + emailUser + " status " + responseActive.get(emailUser));
        if (!responseActive.get(emailUser)) {
            throw new CostumeResponse(HttpStatus.NOT_FOUND, "Activation account fail");
        }
        Employee employee = customeUserDetailService.loadGetDataEmployeeBasedEmail(emailUser);
        employee.setIsactive(true);
        Employee SaveEmployee = dirctRepoEmployee.employeRepository.save(employee);
        return new ResponseEntity<>("Success verified account " + SaveEmployee.getName(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateEmployee(Employee usrEmployee, DTOUpdateProfile request) {
        try {
            log.info("Start Processing update data Employee");
            checkUpdateProfile(request, usrEmployee);
            log.info("Cek data Employee will update");
            Employee saveEmployee = dirctRepoEmployee.employeRepository.save(usrEmployee);
            log.info("Success Update data employee {}", saveEmployee);
            return new ResponseEntity<>(WebResponse.builder().data(saveEmployee.ResponseEmployee(saveEmployee.getName(), saveEmployee.getEmail(), "", saveEmployee.getSalary())).StatusCode(HttpStatus.ACCEPTED.value()).message("Success update data user").build(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Error update profile " + e.getMessage());
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public ResponseEntity<?> loginEmployeService(DTOLogin request) {
        //dirctRepositoryEmployee.employeRepository.find
        try {
            log.info("Start Processing login application {}", request.email());
            Employee employee = dirctRepoEmployee.employeRepository.findByEmail(request.email()).orElseThrow(() -> new CostumeResponse(HttpStatus.BAD_REQUEST, "email and password are not valid"));
            log.warn("Check data employee based on email {}", employee.getEmail());
            if (!EncriptandDecriptPassword.checkPassword(request.password(), employee.getPassword())) {
                log.debug("Error validation login didn't match");
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "email and password are not invalid");
            }
            if (employee.getIsactive() == false || employee.getIsactive() == null) {
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "User account is deactivated");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            log.warn("Warning set Auth user base on login {}", request.email());

            List<String> roles = getRoleEmployee(employee);

            String token = directComService.jwtUtilFilter.generatedToken(employee.getId(), roles);
            log.warn("Generated Token based on login user {} ", employee.getName());
            employee.setToken(token);
            Employee saveEmployee = dirctRepoEmployee.employeRepository.save(employee);
            log.info("Success login employee {}", employee.getName());
            return new ResponseEntity<>(WebResponse.builder().data(employee.ResponseEmployee(saveEmployee.getName(), null, token, 0)).StatusCode(HttpStatus.ACCEPTED.value()).message("Success login user").build(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Error login account " + e.getMessage());
            throw e;
        }

    }

    @Override
    public ResponseEntity<?> reactiveAccount(String emailUser) {
        // TODO Auto-generated method stub
        try {
            Employee userData = customeUserDetailService.loadGetDataEmployeeBasedEmail(emailUser);
            String pathLink = enviromentGetData.getBase_Url() + "/api/auth/employee/en/register/confirm?confirm=" + directComService.jwtUtilFilter.generateTokenReactiveAccount(emailUser);
            Map<String, String> MapContext = new HashMap<>();
            MapContext.put("name", userData.getName());
            MapContext.put("activationLink", pathLink);
            emailServiceSend.sendEmail(userData.getEmail(), "Activation Account", MapContext, "emailTemplate/email-confirmation");
            return new ResponseEntity<>("Please check email to activation account", HttpStatus.OK);
        } catch (Exception e) {
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public ResponseEntity<?> reActiveProfileAccount(String codeUpdateProfile) {
        try {
            log.info("Started update profile account after deactivate account");
            Map<String, Boolean> responseUpdateActive = directComService.jwtUtilFilter.validationUpdateProfile(codeUpdateProfile);
            String emailUpdate = responseUpdateActive.keySet().stream().toList().get(0);
            if (!responseUpdateActive.get(emailUpdate)) {
                throw new CostumeResponse(HttpStatus.NOT_FOUND, "Activation account fail");
            }
            Employee employee = customeUserDetailService.loadGetDataEmployeeBasedEmail(emailUpdate);
            employee.setIsactive(true);
            Employee SaveEmployee = dirctRepoEmployee.employeRepository.save(employee);
            return new ResponseEntity<>("Success active account back " + SaveEmployee.getName(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Error active update profile " + e.getMessage());
            throw new CostumeResponse(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public ResponseEntity<?> insertDataRoleUser(String role) {
        RoleUser userRole = dirctRepoEmployee.customeEmployeRepositor.insertUserRole(role);
        return new ResponseEntity<>("Success insert role " + role, HttpStatus.OK);
    }

}
