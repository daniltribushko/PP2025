package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.DBEntity;

/** Сущность контакта */
@Entity
public class Contact extends DBEntity {
    private String title;

    private String description;

    public Contact() {
        super();
    }

    public Contact(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
