package ru.tdd.backend.model.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dto запроса на регистрацию*/
public class SignUp {
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

    @Schema(
            name = "confirmPassword",
            description = "Пароль для подтверждения",
            type = "string",
            example = "123"
    )
    @NotBlank(message = "Пароль для подтверждения должен быть заполнен")
    private String confirmPassword;

    @Schema(
            name = "lastName",
            description = "Фамилия пользователя",
            type = "string",
            example = "Иванов"
    )
    @NotBlank(message = "Фамилия пользователя должна быть заполнена")
    private String lastName;

    @Schema(
            name = "middleName",
            description = "Отчество пользователя",
            type = "string",
            example = "Иванович"
    )
    private String middleName;

    @Schema(
            name = "firstName",
            description = "Имя пользователя",
            type = "string",
            example = "Иван"
    )
    @NotBlank(message = "Имя пользователя должно быть заполнено")
    private String firstName;

    public SignUp(String email, String password, String confirmPassword, String lastName, String middleName, String firstName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
