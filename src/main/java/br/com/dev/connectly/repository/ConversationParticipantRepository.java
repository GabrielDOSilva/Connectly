package br.com.dev.connectly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.connectly.entity.Conversation;
import br.com.dev.connectly.entity.ConversationParticipant;
import br.com.dev.connectly.entity.Users;

public interface ConversationParticipantRepository
        extends JpaRepository<ConversationParticipant, Long> {

    boolean existsByConversationAndUser(
            Conversation conversation,
            Users user
    );
}