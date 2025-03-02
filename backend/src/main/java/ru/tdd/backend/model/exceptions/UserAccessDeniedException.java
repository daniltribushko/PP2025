package ru.tdd.backend.model.exceptions;

public class UserAccessDeniedException extends ApiException{
    public UserAccessDeniedException(String email) {
        super(403, "Доступ запрещен");
    }
}
