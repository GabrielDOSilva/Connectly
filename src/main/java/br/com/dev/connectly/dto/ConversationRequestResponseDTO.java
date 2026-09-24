package br.com.dev.connectly.dto;

public class ConversationRequestResponseDTO {

    private Long id;
    private String username;
    private String status;

    public ConversationRequestResponseDTO(
            Long id,
            String username,
            String status) {

        this.id = id;
        this.username = username;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }
}