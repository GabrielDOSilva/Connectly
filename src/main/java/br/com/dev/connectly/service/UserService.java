package br.com.dev.connectly.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dev.connectly.dto.UserRequestDTO;
import br.com.dev.connectly.dto.UserResponseDTO;
import br.com.dev.connectly.entity.Users;
import br.com.dev.connectly.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Users createUser(Users user) {
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		return userRepository.save(user);
	}
	
	public UserResponseDTO createUser(UserRequestDTO request) {
		
		Users user = new Users();
		
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		Users savedUser = userRepository.save(user);
		
		return new UserResponseDTO(
				savedUser.getId(),
				savedUser.getUsername(),
				savedUser.getEmail()
				);
		
	}
}
