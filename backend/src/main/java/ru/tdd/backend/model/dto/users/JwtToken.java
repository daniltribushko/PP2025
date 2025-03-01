package ru.tdd.backend.model.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;

/** Dto jwt токена*/
public class JwtToken {
    @Schema(
            name = "token",
            description = "Jwt токен пользователя",
            type = "string",
            example = "test-token"
    )
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
