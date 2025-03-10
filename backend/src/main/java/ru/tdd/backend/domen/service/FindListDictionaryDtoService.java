package ru.tdd.backend.domen.service;

import jakarta.validation.constraints.Min;
import ru.tdd.backend.model.dto.DictionaryDto;

import java.util.List;

public interface FindListDictionaryDtoService<DTO extends DictionaryDto> {
    List<DTO> findAll(
            String text,
            @Min(value = 0, message = "Номер страницы должен быть больше либо равен 0")
            Integer page,
            @Min(value = 1, message = "Количество записей на странице должно быть больше 1")
            Integer perPage
    );
}
