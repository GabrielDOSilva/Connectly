package br.com.dev.connectly.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	public Users createUser(Users users) {
		
		String encodedPassword = passwordEncoder.encode(users.getPassword());
		users.setPassword(encodedPassword);
		
		return userRepository.save(users);
	}
	
}
