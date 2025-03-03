package ru.tdd.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DictionaryDto extends BaseDto {
    @Schema(
            name = "id",
            description = "Идентификатор словаря",
            type = "integer",
            format = "int64",
            example = "1"
    )
    private Long id;
    @Schema(
            name = "description",
            description = "Описание словаря",
            type = "string",
            example = "Тестовое описание"
    )
    private String description;

    public DictionaryDto(Long id, String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
