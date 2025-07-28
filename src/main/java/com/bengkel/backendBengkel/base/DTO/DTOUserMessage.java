package com.bengkel.backendBengkel.base.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOUserMessage {

    private String idConversation;

    private String sender;

    private List<String> receiver;

    private String nameGroup;

    //for content
    private String content;

    private String typeContent;

    private String linkContent;

    private LocalDateTime timestamp;

}
