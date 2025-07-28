package com.bengkel.backendBengkel.messageModule.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bengkel.backendBengkel.base.DTO.ConversationDTO;
import com.bengkel.backendBengkel.base.DTO.DTOUserMessage;
import com.bengkel.backendBengkel.base.exception.CostumeResponse;
import com.bengkel.backendBengkel.base.midlewares.ComponentFinalContextData;
import com.bengkel.backendBengkel.base.midlewares.SequenceGeneratorComponent;
import com.bengkel.backendBengkel.base.responsePage.WebResponse;
import com.bengkel.backendBengkel.base.untilize.directCommonService;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.employeeModule.model.Employee;
import com.bengkel.backendBengkel.messageModule.model.Conversation;
import com.bengkel.backendBengkel.messageModule.model.Messages;
import com.bengkel.backendBengkel.messageModule.model.packModule.StatusMod;
import com.bengkel.backendBengkel.messageModule.service.impl.ImplementasiServiceMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ServiceMessage implements ImplementasiServiceMessage {

    private final directRepositoryEmployee DirectRepository;

    private final directCommonService directCommonService;

    private final SequenceGeneratorComponent seqGeneratorComponent;

    @Override
    public ResponseEntity<?> implementInsertMessage(Employee employee, DTOUserMessage request) {
        // TODO Auto-generated method stub
        List<String> listMember = new ArrayList<>();
        listMember.add(employee.getId());

        if (Objects.isNull(request.getTimestamp()) || request.getTimestamp() == null) {
            request.setTimestamp(LocalDateTime.now());
        }
        for (String userName : request.getReceiver()) {
            Employee userEmployee = DirectRepository.employeRepository.findByUsername(userName).orElseThrow(()
                    -> new CostumeResponse(HttpStatus.NOT_FOUND, "User id is not found for username " + userName));
            listMember.add(userEmployee.getId());
        }
        if (request.getIdConversation().isEmpty()) {
            Conversation conversation = createConversation(request, listMember);
            Messages message = createNewMessageConv(request, conversation, listMember);
            DTOUserMessage dtoUserMessage = DTOUserMessage.builder()
                    .content(message.getContent())
                    .idConversation(conversation.getId().toString())
                    .linkContent(message.getMediaUrl())
                    .nameGroup(conversation.getNameConversation())
                    .typeContent(message.getMessageType())
                    .timestamp(message.getCreateTimestamp())
                    .receiver(request.getReceiver())
                    .sender(request.getSender())
                    .build();
            List<DTOUserMessage> DTOMessages = new ArrayList<>();
            DTOMessages.add(dtoUserMessage);
            ConversationDTO responseConversationDTO = ConversationDTO.builder()
                    .idConversation(conversation.getId())
                    .isGroup(conversation.getIsGroup())
                    .nameConversation(conversation.getNameConversation())
                    .participants(listMember)
                    .messages(DTOMessages).build();

            return new ResponseEntity<>(WebResponse.builder().data(responseConversationDTO).status(true).message("Success send message").StatusCode(HttpStatus.ACCEPTED.value()).build(), HttpStatus.OK);
        } else {
            UUID conversationID = UUID.fromString(request.getIdConversation());
            Conversation conversation = DirectRepository.conversationRepository.findById(conversationID).orElseThrow(() -> new CostumeResponse(HttpStatus.NOT_FOUND, "Conversation is not found"));
            Messages message = createNewMessageConv(request, conversation, listMember);
            return null;
        }
    }

    @Override
    public Object implementDeleteMessage(Employee employee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'implementDeleteMessage'");
    }

    @Override
    public ConversationDTO getConversationMessage(Employee employee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Transactional
    private Messages createNewMessageConv(DTOUserMessage request, Conversation reqConversation, List<String> members) {
        Messages message = new Messages();
        message.setId(UUID.randomUUID());
        message.setContent(request.getContent());
        message.setConversationId(reqConversation.getId());
        message.setSeq(seqGeneratorComponent.generateSequence(ComponentFinalContextData.sequence_Message));
        message.setCreateTimestamp(request.getTimestamp());
        message.setMediaUrl(request.getLinkContent());
        message.setMessageType(request.getTypeContent());
        message.setSenderId(members.get(0));
        ZoneId zona = ZoneId.systemDefault();
        StatusMod statusMod = null;
        Map<String, StatusMod> statusMap = new HashMap<>();
        for (int i = 0; i < members.size(); i++) {
            if (i == 0) {
                continue;
            }
            statusMod = new StatusMod();
            statusMod.setDeliveredAt(request.getTimestamp().atZone(zona).toInstant());
            statusMod.setRead(false);
            statusMap.put(members.get(i), statusMod);
        }
        message.setStatus(statusMap);
        directCommonService.serviceRedisData.insertMessage(message);
        // Messages messageSave = DirectRepository.messageRepository.save(message);
        return message;
    }

    @Transactional
    private Conversation createConversation(DTOUserMessage request, List<String> members) {
        Conversation conversation = new Conversation();
        Boolean statusGroup = !request.getNameGroup().isEmpty();
        conversation.setCreateAt(request.getTimestamp());
        conversation.setNameConversation(request.getNameGroup());
        conversation.setIsGroup(statusGroup);
        conversation.setMembers(members);
        Conversation SaveConversation = DirectRepository.conversationRepository.save(conversation);
        //set for the message
        return SaveConversation;
    }
}
