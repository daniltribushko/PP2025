package ru.tdd.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class BaseDto {
    @Schema(
            name = "name",
            description = "Наименование сущности",
            example = "example",
            type = "string"
    )
    private String name;

    public BaseDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
