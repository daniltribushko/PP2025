package ru.tdd.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/** Базовый класс сущности имеющей поле "Наименование" и "Описание" */
@MappedSuperclass
public abstract class DictionaryEntity extends BaseEntity {
    @Column(nullable = false, length = 2500)
    protected String description;

    public DictionaryEntity(String name, String description) {
        super(name);
        this.description = description;
    }

    public DictionaryEntity() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
