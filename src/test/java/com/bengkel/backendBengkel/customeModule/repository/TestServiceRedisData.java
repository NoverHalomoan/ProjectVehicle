package com.bengkel.backendBengkel.customeModule.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bengkel.backendBengkel.base.Services.ServiceRedisData;
import com.bengkel.backendBengkel.base.untilize.directRepositoryEmployee;
import com.bengkel.backendBengkel.messageModule.model.Conversation;
import com.bengkel.backendBengkel.messageModule.model.Messages;
import com.bengkel.backendBengkel.messageModule.model.packModule.StatusMod;

@SpringBootTest
public class TestServiceRedisData {

    @Autowired
    private ServiceRedisData serviceRedisData;

    @Autowired
    private directRepositoryEmployee dRepositoryEmployee;

    @Test
    @Disabled("Already Test message")
    void TestIntegerationDataTestToRedis() {
        Conversation conversation = createConversation();
        Conversation converSave = dRepositoryEmployee.conversationRepository.save(conversation);
        Messages messages = createMessages(converSave);
        Messages messageSave = dRepositoryEmployee.messageRepository.save(messages);
        serviceRedisData.insertMessage(messageSave);
    }

    @Test
    @Disabled("Just Delete all data in conversation and message")
    void DeleteAllDataInConversation() {
        dRepositoryEmployee.conversationRepository.deleteAll();
        dRepositoryEmployee.messageRepository.deleteAll();
    }

    private Messages createMessages(Conversation converSave) {
        Messages messages = new Messages();
        messages.setContent("Testing Message");
        messages.setConversationId(converSave.getId());
        messages.setCreateTimestamp(LocalDateTime.now());
        messages.setSenderId("e6e1b1ec-c017-43d0-acf8-12a4adb9ad4b");
        messages.setMediaUrl("");
        messages.setMessageType("text");
        messages.setSeq(1l);
        StatusMod status = new StatusMod();
        status.setDeliveredAt(Instant.now());
        status.setRead(false);
        status.setReadAt(null);
        messages.setStatus(Map.of("2c29fc33-1ad9-4c57-a987-a7e0617932a8", status));
        return messages;
    }

    private Conversation createConversation() {
        Conversation conversation = new Conversation();

        List<String> members = new ArrayList<>(Arrays.asList("e6e1b1ec-c017-43d0-acf8-12a4adb9ad4b", "2c29fc33-1ad9-4c57-a987-a7e0617932a8"));
        conversation.setIsGroup(false);
        conversation.setNameConversation("");
        conversation.setCreateAt(LocalDateTime.now());
        conversation.setMembers(members);
        return conversation;
    }

    @Test
    @Disabled("Testng unit Test on repository")
    void TestInsertDataToRedis() {
        Conversation conversation = createConversation();
        Conversation converSave = dRepositoryEmployee.conversationRepository.save(conversation);
        //set conversation
        Messages messages = createMessages(converSave);
        Messages messageSave = dRepositoryEmployee.messageRepository.save(messages);
    }

}
