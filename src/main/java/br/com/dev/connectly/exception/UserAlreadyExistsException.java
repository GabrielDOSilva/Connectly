package br.com.dev.connectly.exception;

public class UserAlreadyExistsException extends RuntimeException{
	
	public UserAlreadyExistsException (String message) {
		super(message);
	}

}
