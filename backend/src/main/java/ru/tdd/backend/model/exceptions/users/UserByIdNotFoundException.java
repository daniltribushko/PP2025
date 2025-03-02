package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class UserByIdNotFoundException extends NotFoundException {
    public UserByIdNotFoundException(Long id) {
        super("Пользователь с указанным идентификатором: " + id + " не найден");
    }
}
