package ru.tdd.backend.model.exceptions.vacancies;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class SkillByIdNotFoundException extends NotFoundException {
    public SkillByIdNotFoundException(Long id) {
        super("Навык с указанным идентификатором " + id + " не найден");
    }
}
