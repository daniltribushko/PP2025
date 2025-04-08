package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class EmployeeByIdNotFoundException extends NotFoundException {
    public EmployeeByIdNotFoundException(Long id) {
        super("Работник с идентификатором " + id + " не найден");
    }
}
