package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class UserByEmailAlreadyExistsException extends NotFoundException {
    public UserByEmailAlreadyExistsException(String email) {
        super("Пользователь с электронным адресом: " + email + " уже существует");
    }
}
