package ru.tdd.backend.model.exceptions.vacancies;

import ru.tdd.backend.model.exceptions.AlreadyExistsException;

public class SkillAlreadyExistException extends AlreadyExistsException {
    public SkillAlreadyExistException(String name) {
        super("Навык: " + name + " уже создан");
    }
}
