package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.ApiException;

public class UserNotOrganisationEmployeeException extends ApiException {
    public UserNotOrganisationEmployeeException(String email) {
        super(403, "Пользователь: " + email + " не является сотружником организации");
    }
}
