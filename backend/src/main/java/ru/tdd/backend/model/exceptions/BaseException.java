package ru.tdd.backend.model.exceptions;

/** Исключение приложения, которое встречается малое количество раз*/
public class BaseException extends ApiException {
    public BaseException(String message) {
        super(400, message);
    }
}
