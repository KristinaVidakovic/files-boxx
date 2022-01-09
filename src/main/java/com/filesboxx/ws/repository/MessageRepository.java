package com.filesboxx.ws.repository;

import com.filesboxx.ws.model.Message;
import com.filesboxx.ws.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<Message> findByChatId(String chatId);
}
