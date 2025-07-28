package com.bengkel.backendBengkel.messageModule.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bengkel.backendBengkel.messageModule.model.packModule.StatusMod;

import lombok.Data;

@Data
@Document(collection = "message")
public class Messages {

    @Id
    private UUID id;

    private Long seq;

    private UUID conversationId;
    private String senderId;
    private String content;
    private String messageType;
    private String mediaUrl;

    @CreatedDate
    private LocalDateTime createTimestamp;
    private Map<String, StatusMod> status;

    // @PrePersist
    // public void generatedId() {
    //     if (id == null) {
    //         id = UUID.randomUUID();
    //     }
    // }
}
