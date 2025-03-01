package ru.tdd.backend.model.exceptions;

public abstract class AlreadyExistsException extends ApiException {
    public AlreadyExistsException(String message) {
        super(409, message);
    }
}
