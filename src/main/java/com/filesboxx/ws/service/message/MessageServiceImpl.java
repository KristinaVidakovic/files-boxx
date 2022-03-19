package com.filesboxx.ws.service.message;

import com.filesboxx.ws.model.Message;
import com.filesboxx.ws.model.MessageStatus;
import com.filesboxx.ws.repository.MessageRepository;
import com.filesboxx.ws.service.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository repository;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private DataSource dataSource;

    @Override
    public Message save(Message chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    @Override
    public List<Message> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = conversationService.getChatId(senderId, recipientId, false);

        List<Message> messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    public Message findById(String id) throws Exception {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new Exception("can't find message (" + id + ")"));
    }

    @Override
    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("UPDATE MESSAGE SET STATUS = ? WHERE SENDER_ID = ? AND RECIPIENT_ID = ?");
        int stat = jdbcTemplate.update(sql, status, senderId, recipientId);
        if (stat != 0) {
            System.out.println("Uspesno");
        } else System.out.println("Neuspesno");
    }
}
