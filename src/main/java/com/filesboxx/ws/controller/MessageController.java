package com.filesboxx.ws.controller;

import com.filesboxx.ws.model.Message;
import com.filesboxx.ws.model.Notification;
import com.filesboxx.ws.service.conversation.ConversationService;
import com.filesboxx.ws.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationService conversationService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        Optional<String> chatId = conversationService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        Message saved = messageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new Notification(
                        saved.getMessageId(),
                        saved.getSenderId(),
                        "Pera"));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) throws Exception {
        return ResponseEntity
                .ok(messageService.findById(id));
    }
}
