package com.bengkel.backendBengkel.employeeModule.repository;

import java.util.List;

import com.bengkel.backendBengkel.employeeModule.model.Department;
import com.bengkel.backendBengkel.employeeModule.model.RoleUser;

public interface CostumeImplementRepo {

    default List<Department> findByDepartmentName(String nameDepartment) {
        return null;
    }

    default RoleUser insertUserRole(String role) {
        return null;
    }

    default List<RoleUser> findByUserrole(List<String> role) {
        return null;
    }
}
