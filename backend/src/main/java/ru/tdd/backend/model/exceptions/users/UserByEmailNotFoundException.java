package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class UserByEmailNotFoundException extends NotFoundException {
    public UserByEmailNotFoundException(String email) {
        super("Пользователь с указанным электронным адресом: " + email + " не найден");
    }
}
