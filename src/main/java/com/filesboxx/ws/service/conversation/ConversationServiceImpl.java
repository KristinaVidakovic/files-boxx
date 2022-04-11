package com.filesboxx.ws.service.conversation;

import com.filesboxx.ws.model.chat.Chat;
import com.filesboxx.ws.model.conversation.Conversation;
import com.filesboxx.ws.repository.conversation.ConversationRepository;
import com.filesboxx.ws.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    ConversationServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Chat> getChatId(UUID senderId, UUID recipientId, boolean createIfNotExist) {
        return conversationRepository
                .findBySenderUserIdAndRecipientUserId(senderId, recipientId)
                .map(Conversation::getChat)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    UUID chatId =
                            UUID.fromString(String.format("%s_%s", senderId, recipientId));

                    Chat chat = new Chat(chatId);

                    Conversation senderRecipient = Conversation
                            .builder()
                            .chat(chat)
                            .sender(userRepository.findByUserId(senderId))
                            .recipient(userRepository.findByUserId(recipientId))
                            .build();

                    Conversation recipientSender = Conversation
                            .builder()
                            .chat(chat)
                            .sender(userRepository.findByUserId(recipientId))
                            .recipient(userRepository.findByUserId(senderId))
                            .build();
                    conversationRepository.save(senderRecipient);
                    conversationRepository.save(recipientSender);

                    return Optional.of(chat);
                });
    }
}
