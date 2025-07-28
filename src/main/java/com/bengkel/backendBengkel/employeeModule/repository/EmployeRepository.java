package com.bengkel.backendBengkel.employeeModule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bengkel.backendBengkel.employeeModule.model.Employee;

@Repository
public interface EmployeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUsername(String username);
}
