package ru.tdd.backend.model.exceptions.organisations;

import ru.tdd.backend.model.exceptions.ApiException;

public class EmployeeDontOrganisationManagerException extends ApiException {
    public EmployeeDontOrganisationManagerException(String email) {
        super(403, "Пользователь: " + email + " не является менеджером организации");
    }
}
