package ru.tdd.backend.domen.service;

import ru.tdd.backend.model.DBEntity;

public interface DtoService <DTO, Entity extends DBEntity>{
    DTO findById(DTO dto);
    DTO create(DTO dto);
    DTO update(DTO dto);
    void delete(DTO dto);
}
