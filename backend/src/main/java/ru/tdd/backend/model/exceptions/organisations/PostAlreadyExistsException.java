package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class PostAlreadyExistsException extends NotFoundException {
    public PostAlreadyExistsException(String name) {
        super("Должность с указаннмы именем: " + name + " не найдена");
    }
}
