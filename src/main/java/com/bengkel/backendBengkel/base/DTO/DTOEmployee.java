package com.bengkel.backendBengkel.base.DTO;

public record DTOEmployee(
        String nameEmployee,
        String username,
        float salary,
        String departmentName,
        String password,
        String email,
        String role
        ) {

}
