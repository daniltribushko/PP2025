package ru.tdd.backend.model.exceptions.users;

import ru.tdd.backend.model.exceptions.ApiException;

public class WrongEmailPasswordException extends ApiException {
    public WrongEmailPasswordException() {
        super(403, "Неверный логи или пароль");
    }
}
