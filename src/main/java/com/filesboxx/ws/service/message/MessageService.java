package com.filesboxx.ws.service.message;

import com.filesboxx.ws.model.Message;
import com.filesboxx.ws.model.MessageStatus;

import java.util.List;

public interface MessageService {

    Message save(Message chatMessage);

    long countNewMessages(String senderId, String recipientId);

    List<Message> findChatMessages(String senderId, String recipientId);

    Message findById(String id) throws Exception;

    void updateStatuses(String senderId, String recipientId, MessageStatus status);
}
