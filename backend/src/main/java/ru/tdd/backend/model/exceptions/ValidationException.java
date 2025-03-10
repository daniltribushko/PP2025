package ru.tdd.backend.model.exceptions;

public class ValidationException extends ApiException{
    public ValidationException(String message) {
        super(422, message);
    }
}
