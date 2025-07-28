package com.bengkel.backendBengkel.messageModule.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Document(collection = "conversation")
public class Conversation {

    @Id
    private UUID id = UUID.randomUUID();

    private Boolean isGroup;

    private String nameConversation;

    private List<String> members;

    private LocalDateTime createAt;

}
