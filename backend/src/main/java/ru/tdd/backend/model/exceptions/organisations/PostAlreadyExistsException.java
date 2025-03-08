package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.AlreadyExistsException;

public class PostAlreadyExistsException extends AlreadyExistsException {
    public PostAlreadyExistsException(String name) {
        super("Должность с указаннмы именем: " + name + " уже создана");
    }
}
