package ru.tdd.backend.model.exceptions;

import java.time.LocalDateTime;

/** Базовый класс сущности */
public abstract class ApiException extends RuntimeException {
    protected int code;
    protected LocalDateTime timestamp;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
