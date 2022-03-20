package com.filesboxx.ws.repository;

import com.filesboxx.ws.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Optional<Conversation> findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);

}
