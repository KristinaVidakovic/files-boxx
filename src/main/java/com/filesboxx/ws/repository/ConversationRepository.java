package com.filesboxx.ws.repository;

import com.filesboxx.ws.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation,String> {

    Optional<Conversation> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
