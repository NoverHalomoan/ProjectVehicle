package com.bengkel.backendBengkel.employeeModule.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "department")
public class Department {

    @Id
    private String id;

    private String name;

    public Department(String id, String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

}
