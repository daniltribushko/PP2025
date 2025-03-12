package ru.tdd.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FileDto {
    @Schema(
            name = "fileName",
            description = "Имя файла",
            format = "string",
            example = "1.txt"
    )
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
