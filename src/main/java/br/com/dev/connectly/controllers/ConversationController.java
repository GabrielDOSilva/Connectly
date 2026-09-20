package br.com.dev.connectly.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.connectly.dto.ConversationRequestDTO;
import br.com.dev.connectly.entity.Conversation;
import br.com.dev.connectly.service.ConversationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<Conversation> create(
            @Valid @RequestBody ConversationRequestDTO request) {

        Conversation conversation = conversationService.createConversation(
                request.getFirstUserId(),
                request.getSecondUserId()
        );

        return ResponseEntity.ok(conversation);
    }
}