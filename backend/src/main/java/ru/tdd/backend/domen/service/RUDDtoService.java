package ru.tdd.backend.domen.service;

/** Сервис для чтения, обновления, удаления сущностей*/
public interface RUDDtoService<DTO> {
    DTO getById(Long id);
    DTO update(DTO dto);
    void delete(Long id);
}
