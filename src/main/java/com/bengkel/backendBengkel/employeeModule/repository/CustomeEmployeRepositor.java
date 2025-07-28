package com.bengkel.backendBengkel.employeeModule.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.employeeModule.model.Department;
import com.bengkel.backendBengkel.employeeModule.model.RoleUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CustomeEmployeRepositor implements CostumeImplementRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> findByDepartmentName(String nameDepartment) {
        // TODO Auto-generated method stub
        if (nameDepartment.isEmpty() || Objects.isNull(nameDepartment)) {
            nameDepartment = "Support";
        }
        String sql = "select u from Department u where lower(u.name) LIKE :name";

        return entityManager.createQuery(sql, Department.class)
                .setParameter("name", "%" + nameDepartment.toLowerCase() + "%").getResultList();
    }

    @Transactional
    @Override
    public RoleUser insertUserRole(String role) {
        // TODO Auto-generated method stub
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        entityManager.persist(roleUser);
        return roleUser;
    }

    private String queryInSearch(List<String> dataList) {
        String combineSearch = "(";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == dataList.size() - 1) {
                combineSearch += "'" + dataList.get(i) + "')";
                continue;
            }
            combineSearch += "'" + dataList.get(i) + "',";
        }
        return combineSearch;
    }

    @Override
    public List<RoleUser> findByUserrole(List<String> role) {
        // TODO Auto-generated method stub
        //String sql = "select CAST(u.id AS CHAR) as id, u.role from RoleUser u where lower(u.role) IN ('admin', 'user')";
        if (role.isEmpty()) {
            role = new ArrayList<>();
            role.add("ADMIN");
        }
        for (String roles : role) {
            System.out.println("Data Role " + roles);
        }
        //String roless = queryInSearch(role);
        String sql = "SELECT u FROM RoleUser u WHERE LOWER(u.role) IN :roleUs";
        List<RoleUser> results = entityManager.createQuery(sql, RoleUser.class)
                .setParameter("roleUs", role.stream().map(String::toLowerCase).toList())
                .getResultList();

        // List<RoleUserList> dtos = results.stream()
        //         .map(row -> new DTORoleUser((Long) row[0], (String) row[1]))
        //         .toList();
        System.out.println("Data testing " + results.size());
        // for (Object[] row : results) {
        //     RoleUserList.add(new RoleUser(UUID.fromString(row[0].toString()), row[1].toString()));
        // }
        // for (RoleUser rolle : RoleUserList) {
        //     System.out.println("Data " + RoleUserList);
        // }
        return results;
    }

}
