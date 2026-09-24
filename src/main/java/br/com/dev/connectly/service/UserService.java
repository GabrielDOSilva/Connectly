package br.com.dev.connectly.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dev.connectly.dto.UserRequestDTO;
import br.com.dev.connectly.dto.UserResponseDTO;
import br.com.dev.connectly.dto.UserSearchResponseDTO;
import br.com.dev.connectly.entity.Users;
import br.com.dev.connectly.exception.UserAlreadyExistsException;
import br.com.dev.connectly.exception.UserNotFoundException;
import br.com.dev.connectly.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailCryptoService emailCryptoService;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailCryptoService emailCryptoService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailCryptoService = emailCryptoService;
	}
	
	public UserSearchResponseDTO findByUsername(String username) {
		
		Users user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
		
		return new UserSearchResponseDTO(user.getUsername());
	}
	

	
	public UserResponseDTO createUser(UserRequestDTO request) {
		
		if
		(userRepository.existsByUsername(request.getUsername())) {
			throw new
			UserAlreadyExistsException("Username already exists");
		}
		
		String emailHash = emailCryptoService.generateHash(request.getEmail());
		
		if
		(userRepository.existsByEmailHash(emailHash)) {
			throw new
			UserAlreadyExistsException("Email already exists");
		}
		
		Users user = new Users();
		
		user.setUsername(request.getUsername());
		user.setEmailEncrypted(emailCryptoService.encrypt(request.getEmail()));
		user.setEmailHash(emailHash);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		Users savedUser = userRepository.save(user);
		
		return new UserResponseDTO(
				savedUser.getId(),
				savedUser.getUsername(),
				emailCryptoService.decrypt(savedUser.getEmailEncrypted())
				);
		
	}
	
}

	
