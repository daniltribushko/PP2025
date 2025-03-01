package ru.tdd.backend.model.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dto запроса на авторизацию*/
public class SignIn {
    @Schema(
            name = "email",
            description = "Электронный адрес пользователя",
            type = "string",
            example = "example@gmail.com",
            minLength = 7,
            maxLength = 55
    )
    @Email(message = "Должен имет формат адреса электронной почты")
    @Size(min = 7, max = 55, message = "Электронный адрес пользователя, должен иметь длину от 7 до 50 символов")
    private String email;

    @Schema(
            name = "password",
            description = "Пароль пользователя",
            type = "string",
            example = "123"
    )
    @NotBlank(message = "Пароль должен быть заполнен")
    private String password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
