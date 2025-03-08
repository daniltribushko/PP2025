package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.ApiException;

public class UserNotAdminException extends ApiException {
    public UserNotAdminException(String email) {
        super(403, "Пользователь: " + email + " не является администратором");
    }
}
