package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class UserByNameNotFoundException extends NotFoundException {
    public UserByNameNotFoundException(String email) {
        super("Пользователь: " + email + " не найден");
    }
}
