package br.com.dev.connectly.dto;



public class ConversationRequestDTO {

  
	private String username;
    
    public ConversationRequestDTO(String username) {
     
    	this.username = username;
    }
    
    
    public String getUsername() {
        return username;
    }

}