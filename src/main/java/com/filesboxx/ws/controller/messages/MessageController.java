package com.filesboxx.ws.controller.messages;

import com.filesboxx.ws.model.message.Message;
import com.filesboxx.ws.model.notification.Notification;
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
import java.util.UUID;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ConversationService conversationService;

    @Autowired
    MessageController(SimpMessagingTemplate messagingTemplate, MessageService messageService, ConversationService conversationService){
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.conversationService = conversationService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        Optional<UUID> chatId = conversationService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        Message saved = messageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientId()),"/queue/messages",
                new Notification(
                        saved.getMessageId(),
                        saved.getSenderId(),
                        saved.getText()));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable UUID senderId,
            @PathVariable UUID recipientId) {

        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable UUID senderId,
                                                @PathVariable UUID recipientId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable UUID id) throws Exception {
        return ResponseEntity
                .ok(messageService.findById(id));
    }

}
