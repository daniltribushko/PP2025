package ru.tdd.backend.model.entities;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.DBEntity;
import ru.tdd.backend.model.dto.DtoEntity;
import ru.tdd.backend.model.dto.FileDto;

import java.io.File;

@Entity
public class DBFile extends DBEntity implements DtoEntity<FileDto> {
    private String fileName;

    public DBFile(String fileName) {
        this.fileName = fileName;
    }

    public DBFile() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return new File(fileName);
    }

    @Override
    public FileDto toDto() {
        return new FileDto(fileName);
    }
}
