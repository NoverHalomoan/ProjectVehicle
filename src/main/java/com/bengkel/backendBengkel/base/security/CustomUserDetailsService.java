package com.bengkel.backendBengkel.base.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final directRepositoryEmployee dRepostoryEmployee;

    public CustomUserDetailsService(directRepositoryEmployee dRepostoryEmployee) {
        this.dRepostoryEmployee = dRepostoryEmployee;
    }

    public Employee loadGetDataEmployee(String idUser) {
        Employee getEmployee = dRepostoryEmployee.employeRepository.findById(idUser).orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "User is not found "));
        return getEmployee;
    }

    public Employee loadGetDataEmployeeBasedEmail(String email) {
        Employee employee = dRepostoryEmployee.employeRepository.findByEmail(email).orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "User is not found"));
        return employee;
    }

    // private direct
    @Override
    public UserDetails loadUserByUsername(String IdUser) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        // log.info("Hey Id User " + IdUser);
        Employee employee = dRepostoryEmployee.employeRepository.findById(IdUser).orElse(null);

        if (employee == null) {
            employee = dRepostoryEmployee.employeRepository.findByEmail(IdUser)
                    .orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "User not found di Details"));
        }
        // log.info("Hey Username " + employee.getUsername());
        // log.info("Hey Password " + employee.getPassword());
        // log.info("Hey auth " + getAuthorities(employee.getRole()));
        return new org.springframework.security.core.userdetails.User(
                employee.getUsername(), // 
                employee.getPassword(),
                getAuthorities(employee.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

}
