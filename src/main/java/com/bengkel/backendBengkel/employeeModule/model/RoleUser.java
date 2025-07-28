package com.bengkel.backendBengkel.employeeModule.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roleuser")
public class RoleUser {

    @Id
    @Column(name = "id")
    // @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String role;

    public RoleUser(String role) {
        this.id = UUID.randomUUID().toString();
        this.role = role;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleUser roleUser = (RoleUser) o;
        return Objects.equals(id, roleUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
