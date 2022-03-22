package com.filesboxx.ws.service.conversation;

import java.util.Optional;
import java.util.UUID;

public interface ConversationService {

    Optional<UUID> getChatId(UUID senderId, UUID recipientId, boolean createIfNotExist);
}
