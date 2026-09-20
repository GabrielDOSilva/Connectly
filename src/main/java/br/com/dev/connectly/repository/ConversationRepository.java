package br.com.dev.connectly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.connectly.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}