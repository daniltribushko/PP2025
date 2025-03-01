package ru.tdd.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/** Базовый класс с названием */
@MappedSuperclass
public abstract class BaseEntity extends DBEntity {
    @Column(unique = true, nullable = false)
    protected String name;

    public BaseEntity(String name) {
        this.name = name;
    }

    public BaseEntity() {
        super();
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
