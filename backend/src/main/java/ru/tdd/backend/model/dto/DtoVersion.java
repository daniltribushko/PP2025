package ru.tdd.backend.model.dto;

import java.time.LocalDateTime;

public class DtoVersion {
    protected Long id;
    protected LocalDateTime creationDate;
    protected LocalDateTime updateDate;

    public DtoVersion(Long id, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
