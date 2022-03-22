package com.filesboxx.ws.service.conversation;

import com.filesboxx.ws.model.conversation.Conversation;
import com.filesboxx.ws.repository.conversation.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Optional<UUID> getChatId(UUID senderId, UUID recipientId, boolean createIfNotExist) {
        return conversationRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Conversation::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    UUID chatId =
                            UUID.fromString(String.format("%s_%s", senderId, recipientId));

                    Conversation senderRecipient = Conversation
                            .builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    Conversation recipientSender = Conversation
                            .builder()
                            .chatId(chatId)
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();
                    conversationRepository.save(senderRecipient);
                    conversationRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}
