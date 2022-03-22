package com.filesboxx.ws.repository.message;

import com.filesboxx.ws.model.message.Message;
import com.filesboxx.ws.model.message.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    long countBySenderIdAndRecipientIdAndStatus(UUID senderId, UUID recipientId, MessageStatus status);

    List<Message> findByChatId(UUID chatId);
}
