package com.bengkel.backendBengkel.employeeModule.service.implService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.DTOUpdateProfile;
import com.bengkel.backendBengkel.base.Services.EmailServiceSend;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.security.CustomUserDetailsService;
import com.bengkel.backendBengkel.base.security.EncriptandDecriptPassword;
import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;
import com.bengkel.backendBengkel.base.untilize.directCommonService;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.employeeModule.model.RoleUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ImplementServiceEmployee {

    protected final directRepositoryEmployee dirctRepoEmployee;

    protected final AuthenticationManager authenticationManager;

    protected final CustomUserDetailsService customeUserDetailService;

    protected final EmailServiceSend emailServiceSend;

    protected final directCommonService directComService;

    protected final EnviromentGetData enviromentGetData;

    private Employee deleteALLRoleUsers(Employee employee) {
        try {
            Employee newEmployee = customeUserDetailService.loadGetDataEmployee(employee.getId());
            log.info("Start delete data roless");
            // for (RoleUser role : newEmployee.getRoles()) {
            //     role.getEmployees().remove(newEmployee);
            // }
            newEmployee.getRoles().clear();
            log.info("Delete all role in user ");
            Employee employeeSave = dirctRepoEmployee.employeRepository.save(newEmployee);
            return employeeSave;
        } catch (AssertionError | Exception a) {
            log.debug("Errror debug " + a.getMessage());
        }
        // TODO: handle exception
        return employee;

    }

    @Transactional
    protected List<String> getRoleEmployee(Employee employee) {
        return employee.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    protected void checkUpdateProfile(DTOUpdateProfile request, Employee requestEmployee) {
        log.info("Detail User " + request);
        if (!requestEmployee.getIsactive()) {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "activation user is not active");
        }
        Employee employee = deleteALLRoleUsers(requestEmployee);
        log.info("Detail Test " + employee);
        if (!Objects.isNull(request.nameEmployee())) {
            employee.setName(request.nameEmployee());
        }
        if (!Objects.isNull(request.email())) {
            employee.setEmail(request.email());
        }
        if (!Objects.isNull(request.password())) {
            employee.setPassword(EncriptandDecriptPassword.encriptPassword(request.password()));
        }
        if (request.salary() > 0.0f) {
            employee.setSalary(request.salary());
        }
        if (!Objects.isNull(request.profilepic())) {
            employee.setProfilepic(request.profilepic());
        }
        if (!Objects.isNull(request.role()) && !request.role().isEmpty()) {
            log.info("Set Data for role");
            List<RoleUser> ListRoleUser = dirctRepoEmployee.customeEmployeRepositor.findByUserrole(request.role());
            log.info("Set Data for role didapat : " + ListRoleUser.size());
            if (ListRoleUser.isEmpty()) {
                throw new CostumeResponse(HttpStatus.BAD_REQUEST, "role is not found");
            }
            log.info("Already get data for role");
            employee.setRole(ListRoleUser.get(0).getRole());
            log.info("Time set to roless");
            for (RoleUser roleData : ListRoleUser) {
                employee.getRoles().add(roleData);
            }

        }
    }

    protected Boolean checkregistrationAccount(String username, String email) {
        Optional<Employee> employee = null;
        if (!Objects.isNull(username)) {
            employee = dirctRepoEmployee.employeRepository.findByUsername(username);
        }
        if (!Objects.isNull(email)) {
            employee = dirctRepoEmployee.employeRepository.findByEmail(email);
        }

        return employee.isPresent();
    }

}
