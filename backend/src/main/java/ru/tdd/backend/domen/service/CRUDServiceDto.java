package ru.tdd.backend.domen.service;

public interface CRUDServiceDto<DTO> extends RUDDtoService<DTO> {
    DTO create(DTO dto);
}
