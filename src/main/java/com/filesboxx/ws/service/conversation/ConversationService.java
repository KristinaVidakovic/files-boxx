package com.filesboxx.ws.service.conversation;

import java.util.Optional;

public interface ConversationService {

    Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);

}
