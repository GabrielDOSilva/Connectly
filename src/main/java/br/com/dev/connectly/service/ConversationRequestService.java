package br.com.dev.connectly.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.dev.connectly.dto.ConversationRequestDTO;
import br.com.dev.connectly.entity.ConversationRequest;
import br.com.dev.connectly.entity.ConversationRequestStatus;
import br.com.dev.connectly.entity.Users;
import br.com.dev.connectly.exception.UserNotFoundException;
import br.com.dev.connectly.repository.ConversationRequestRepository;
import br.com.dev.connectly.repository.UserRepository;
import br.com.dev.connectly.dto.ConversationRequestResponseDTO;

@Service
public class ConversationRequestService {

	private final UserRepository userRepository;
	private final ConversationRequestRepository conversationRequestRepository;
	
	public ConversationRequestService(UserRepository userRepository, ConversationRequestRepository conversationRequestRepository) {
		this.userRepository = userRepository;
		this.conversationRequestRepository = conversationRequestRepository;
	}
	
	public void createRequest(ConversationRequestDTO request) {

		Users receiver = userRepository.findByUsername(request.getUsername())
		        .orElseThrow(() -> new UserNotFoundException("User not found"));
		
		String senderUsername = SecurityContextHolder.getContext()
		        .getAuthentication()
		        .getName();
		
		Users sender = userRepository.findByUsername(senderUsername)
		        .orElseThrow(() -> new UserNotFoundException("User not found"));
		
		if (sender.getId().equals(receiver.getId())) {
		    throw new IllegalArgumentException("You cannot send a request to yourself");
		}
		
		boolean requestExists = conversationRequestRepository.existsBetweenUsers(
		        sender,
		        receiver,
		        ConversationRequestStatus.PENDING
		);
		
		if (requestExists) {
		    throw new IllegalArgumentException(
		            "A pending request already exists between these users");
		}
		
		ConversationRequest conversationRequest = new ConversationRequest();
		
		conversationRequest.setSender(sender);
		conversationRequest.setReceiver(receiver);
		conversationRequest.setStatus(ConversationRequestStatus.PENDING);
		conversationRequest.setCreatedAt(LocalDateTime.now());
		conversationRequestRepository.save(conversationRequest);
		
	}
	
	public List<ConversationRequestResponseDTO> findPendingRequests() {

	    String username = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    Users receiver = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    return conversationRequestRepository
	            .findByReceiverAndStatus(
	                    receiver,
	                    ConversationRequestStatus.PENDING
	            )
	            .stream()
	            .map(request -> new ConversationRequestResponseDTO(
	                    request.getId(),
	                    request.getSender().getUsername(),
	                    request.getStatus().name()
	            ))
	            .toList();
	}
	
}
