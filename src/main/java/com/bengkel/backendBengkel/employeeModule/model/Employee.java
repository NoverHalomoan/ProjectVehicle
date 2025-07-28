package com.bengkel.backendBengkel.employeeModule.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bengkel.backendBengkel.base.responsePage.ResponseDataEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Fetch;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements UserDetails {

    @Id
    private String id;

    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    private float salary;

    private String role;

    private String password;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private Boolean isactive;

    private String profilepic;

    @Transient
    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_employees",
            joinColumns = @JoinColumn(name = "employe_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleUser> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    public void setCreateTime() {
        createTime = LocalDateTime.now();
    }

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(RoleData -> new SimpleGrantedAuthority(RoleData.getRole()))
                .collect(Collectors.toSet());
    }

    public Employee(Department department, String name, float salary, String password, String username, String email, String role) {
        this.department = department;
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.salary = salary;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getFormatSalary() {
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(this.salary);
    }

    public ResponseDataEntity ResponseEmployee(String name, String username, String token, float salary) {
        return ResponseDataEntity.builder().name(name).salary(getFormatSalary()).token(token).build();
    }

}
