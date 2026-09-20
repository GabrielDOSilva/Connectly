package br.com.dev.connectly.service;

import org.springframework.stereotype.Service;

import br.com.dev.connectly.entity.Conversation;
import br.com.dev.connectly.entity.ConversationParticipant;
import br.com.dev.connectly.entity.Users;
import br.com.dev.connectly.repository.ConversationParticipantRepository;
import br.com.dev.connectly.repository.ConversationRepository;
import br.com.dev.connectly.repository.UserRepository;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            ConversationParticipantRepository participantRepository,
            UserRepository userRepository) {

        this.conversationRepository = conversationRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    public Conversation createConversation(Long firstUserId, Long secondUserId) {

        Users firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() -> new RuntimeException("First user not found"));

        Users secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() -> new RuntimeException("Second user not found"));

        Conversation conversation = new Conversation();

        conversation = conversationRepository.save(conversation);

        ConversationParticipant firstParticipant =
                new ConversationParticipant();

        firstParticipant.setConversation(conversation);
        firstParticipant.setUser(firstUser);

        participantRepository.save(firstParticipant);

        ConversationParticipant secondParticipant =
                new ConversationParticipant();

        secondParticipant.setConversation(conversation);
        secondParticipant.setUser(secondUser);

        participantRepository.save(secondParticipant);

        return conversation;
    }
}