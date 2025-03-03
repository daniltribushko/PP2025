package ru.tdd.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class BaseDto {
    @Schema(
            name = "name",
            description = "Наименование сущности",
            example = "example",
            type = "string"
    )
    @NotNull(message = "Название не должно быть пустым")
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
