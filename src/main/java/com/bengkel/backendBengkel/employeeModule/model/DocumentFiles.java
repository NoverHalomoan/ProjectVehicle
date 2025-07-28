package com.bengkel.backendBengkel.employeeModule.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "documentfiles")
public class DocumentFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "mimetype")
    private String mimeType;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    private Double size;

    @Column(name = "filename", unique = true)
    private String fileName;

    @Column(name = "originalname")
    private String originalName;

    @Lob
    @Column(name = "base64_json")
    private String base64Json;

    @Column(name = "typefile")
    private String typeFile;

    @PrePersist
    public void setCreateAT() {
        this.createAt = LocalDateTime.now();
    }

}
