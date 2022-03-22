package com.filesboxx.ws.service.message;

import com.filesboxx.ws.model.message.Message;
import com.filesboxx.ws.model.message.MessageStatus;
import com.filesboxx.ws.repository.message.MessageRepository;
import com.filesboxx.ws.service.conversation.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageRepository repository;
    private final ConversationService conversationService;
    private final DataSource dataSource;

    @Autowired
    MessageServiceImpl(MessageRepository repository, ConversationService conversationService, DataSource dataSource) {
        this.repository = repository;
        this.conversationService = conversationService;
        this.dataSource = dataSource;
    }

    @Override
    public Message save(Message chatMessage) {
        log.info("Method for saving message started.");

        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);

        log.info("Message successfully saved.");
        return chatMessage;
    }

    @Override
    public long countNewMessages(UUID senderId, UUID recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    @Override
    public List<Message> findChatMessages(UUID senderId, UUID recipientId) {
        log.info("Method for finding chat started.");

        Optional<UUID> chatId = conversationService.getChatId(senderId, recipientId, false);

        List<Message> messages =
                chatId.map(repository::findByChatId).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        log.info("Chat messages successfully found.");
        return messages;
    }

    @Override
    public Message findById(UUID id) throws Exception {
        log.info("Method for getting message by message ID started.");

        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    log.info("Message by ID successfully found.");
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new Exception("can't find message (" + id + ")"));
    }

    @Override
    public void updateStatuses(UUID senderId, UUID recipientId, MessageStatus status) {
        log.info("Method for updating message status started.");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("UPDATE MESSAGE SET STATUS = ? WHERE SENDER_ID = ? AND RECIPIENT_ID = ?");
        int stat = jdbcTemplate.update(sql, status, senderId, recipientId);

        if (stat != 0) {
            log.info("Message status successfully updated.");
        } else log.error("Failed updating message status.");
    }
}
