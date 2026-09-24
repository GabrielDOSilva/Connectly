package br.com.dev.connectly.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.connectly.dto.ConversationRequestDTO;
import br.com.dev.connectly.service.ConversationRequestService;
import br.com.dev.connectly.dto.ConversationRequestResponseDTO;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/conversation-requests")
public class ConversationController {

	private final ConversationRequestService conversationRequestService;

	public ConversationController(ConversationRequestService conversationRequestService) {
	    this.conversationRequestService = conversationRequestService;
	}

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody ConversationRequestDTO request) {

    	conversationRequestService.createRequest(request);
    	
    	return ResponseEntity.noContent().build();

    }
    
    @GetMapping
    public ResponseEntity<List<ConversationRequestResponseDTO>> findPendingRequests() {

        List<ConversationRequestResponseDTO> requests =
                conversationRequestService.findPendingRequests();

        return ResponseEntity.ok(requests);
    }
}