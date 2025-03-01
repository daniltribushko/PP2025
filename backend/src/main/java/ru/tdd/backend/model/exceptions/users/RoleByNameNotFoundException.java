package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class RoleByNameNotFoundException extends NotFoundException {
    public RoleByNameNotFoundException(String name) {
        super("Роль пользователя: " + name + " не найдена");
    }
}
