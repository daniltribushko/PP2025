package ru.tdd.backend.model.dto.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionDTO {
    @Schema(
            name = "code",
            description = "Http статус ошибки",
            type = "int",
            format = "int32",
            example = "400"
    )
    private int code;
    @Schema(
            name = "message",
            description = "Текст ошибки",
            type = "string",
            example = "Bad request"
    )
    private String message;


    private Map<String, String> errors;

    @Schema(
            name = "timestamp",
            description = "Время возникновения ошибки"
    )
    private LocalDateTime timestamp;

    public ExceptionDTO(int code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        errors = null;
    }

    public ExceptionDTO(int code, Map<String, String> errors, LocalDateTime timestamp) {
        this.code = code;
        this.errors = errors;
        this.timestamp = timestamp;
        message = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
