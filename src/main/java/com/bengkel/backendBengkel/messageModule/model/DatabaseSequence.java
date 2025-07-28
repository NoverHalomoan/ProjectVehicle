package com.bengkel.backendBengkel.messageModule.model;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "database_sequence")
@Data
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;

}
