package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class OrganisationByIdNotFoundException extends NotFoundException {
    public OrganisationByIdNotFoundException(Long id) {
        super("Организация с идентификатором" + id + " не найедна");
    }
}
