package br.com.dev.connectly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "email_encrypted", nullable = false)
    private String emailEncrypted;

    @Column(name = "email_hash", unique = true, nullable = false)
    private String emailHash;

    @Column(nullable = false)
    private String password;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailEncrypted() {
		return emailEncrypted;
	}

	public void setEmailEncrypted(String emailEncrypted) {
		this.emailEncrypted = emailEncrypted;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
