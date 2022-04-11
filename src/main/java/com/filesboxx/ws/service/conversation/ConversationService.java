package com.filesboxx.ws.service.conversation;

import com.filesboxx.ws.model.chat.Chat;

import java.util.Optional;
import java.util.UUID;

public interface ConversationService {

    Optional<Chat> getChatId(UUID senderId, UUID recipientId, boolean createIfNotExist);
}
