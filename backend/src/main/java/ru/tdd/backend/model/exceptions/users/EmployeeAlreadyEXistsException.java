package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.AlreadyExistsException;

public class EmployeeAlreadyEXistsException extends AlreadyExistsException {
    public EmployeeAlreadyEXistsException(String email) {
        super("Работник: " + email + " уже добавлен");
    }
}
