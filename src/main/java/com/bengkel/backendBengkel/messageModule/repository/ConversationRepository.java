package com.bengkel.backendBengkel.messageModule.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bengkel.backendBengkel.messageModule.model.Conversation;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, UUID> {

}
