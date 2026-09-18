package br.com.dev.connectly.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.connectly.dto.UserRequestDTO;
import br.com.dev.connectly.dto.UserResponseDTO;
import br.com.dev.connectly.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;
	
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request){
		
		UserResponseDTO response = userService.createUser(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
		
		UserResponseDTO response = userService.findById(id);
		
		return ResponseEntity.ok(response);
		
	}
	
}
