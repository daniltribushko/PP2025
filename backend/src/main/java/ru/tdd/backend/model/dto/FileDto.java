package ru.tdd.backend.model.dto;

public class FileDto {
    private String fileName;

    public FileDto(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
