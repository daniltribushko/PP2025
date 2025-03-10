package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class OrganisationTagByIdNotFoundException extends NotFoundException {
    public OrganisationTagByIdNotFoundException(Long id) {
        super("Тэг организации с идентификатором: " + id + " не найдена");
    }
}
