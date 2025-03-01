package ru.tdd.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/** Класс сущности с датой создания и обновления*/
@MappedSuperclass
public abstract class EntityVersion extends DBEntity {
    @Column(nullable = false)
    protected final LocalDateTime creationDate;
    @Column(nullable = false)
    protected LocalDateTime updateDate;

    public EntityVersion() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
