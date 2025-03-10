package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.AlreadyExistsException;

public class OrganisationAlreadyExistException extends AlreadyExistsException {
    public OrganisationAlreadyExistException(String name) {
        super("Организация с указанным именем: " + name + " уже создана");
    }
}
