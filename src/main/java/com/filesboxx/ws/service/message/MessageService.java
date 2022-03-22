package com.filesboxx.ws.service.message;

import com.filesboxx.ws.model.message.Message;
import com.filesboxx.ws.model.message.MessageStatus;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message save(Message chatMessage);

    long countNewMessages(UUID senderId, UUID recipientId);

    List<Message> findChatMessages(UUID senderId, UUID recipientId);

    Message findById(UUID id) throws Exception;

    void updateStatuses(UUID senderId, UUID recipientId, MessageStatus status);
}
