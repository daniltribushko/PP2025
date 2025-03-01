package ru.tdd.backend.model.exceptions;

/** Класс исключения, обьект не найден */
public abstract class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(404,  message);
    }
}
