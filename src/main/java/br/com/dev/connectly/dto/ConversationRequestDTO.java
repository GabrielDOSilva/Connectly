package br.com.dev.connectly.dto;

import jakarta.validation.constraints.NotNull;

public class ConversationRequestDTO {

    @NotNull
    private Long firstUserId;

    @NotNull
    private Long secondUserId;

    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }
}