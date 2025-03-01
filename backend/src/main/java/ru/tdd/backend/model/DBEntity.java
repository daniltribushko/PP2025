package ru.tdd.backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/** Базовый класс сущности */
@MappedSuperclass
public abstract class DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public DBEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DBEntity dbEntity = (DBEntity) o;
        return Objects.equals(id, dbEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
