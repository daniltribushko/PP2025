package ru.tdd.backend.model.entities.users;

/** Состояние пользователя */
public enum UserState {
    ACTIVE("Активен"),
    DELETED("Удален"),
    BANNED("Заблокирован");

    private String name;

    UserState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
