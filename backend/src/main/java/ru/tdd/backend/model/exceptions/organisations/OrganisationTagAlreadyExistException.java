package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.AlreadyExistsException;

public class OrganisationTagAlreadyExistException extends AlreadyExistsException {
    public OrganisationTagAlreadyExistException(String name) {
        super("Тэг организации с указанным именем: " + name + " уже создана");
    }
}
