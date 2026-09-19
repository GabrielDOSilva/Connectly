package br.com.dev.connectly.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dev.connectly.dto.LoginRequestDTO;
import br.com.dev.connectly.dto.LoginResponseDTO;
import br.com.dev.connectly.entity.Users;
import br.com.dev.connectly.repository.UserRepository;
import br.com.dev.connectly.exception.InvalidCredentialsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

    	Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(
                    "Invalid username or password");
        }
        
        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponseDTO(token);
    }
}