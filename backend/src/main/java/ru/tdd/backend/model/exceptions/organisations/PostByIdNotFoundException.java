package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class PostByIdNotFoundException extends NotFoundException {
    public PostByIdNotFoundException(Long id) {
        super("Должность с идентификатором: " + id + " не найдена");
    }
}
