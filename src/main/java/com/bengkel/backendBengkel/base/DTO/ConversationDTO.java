package com.bengkel.backendBengkel.base.DTO;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationDTO {

    private UUID idConversation;

    private Boolean isGroup;

    private String nameConversation;

    private List<String> participants;

    private List<DTOUserMessage> messages;

}
