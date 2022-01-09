package com.filesboxx.ws.service.conversation;

import com.filesboxx.ws.model.Conversation;
import com.filesboxx.ws.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationService{

    @Autowired
    private ConversationRepository conversationRepository;
    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        return conversationRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Conversation::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    String chatId =
                            String.format("%s_%s", senderId, recipientId);

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
