package com.bengkel.backendBengkel.base.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.bengkel.backendBengkel.messageModule.model.Messages;

@Component
public class ServiceRedisData {

    private final RedisTemplate<String, Object> redisTemplate;

    public ServiceRedisData(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void insertMessage(Messages message) {
        String key = "conversation:" + message.getConversationId() + ":messages";
        redisTemplate.opsForValue().set(key, message);
    }

    public List<Object> getMessage(UUID conversationID) {
        String key = "conversation:" + conversationID + ":messages";
        return redisTemplate.opsForList().range(key, 0, -1);

    }

}
