package ru.tdd.backend.domen.service;

import ru.tdd.backend.model.BaseEntity;

/** Сервис для работы с сущностями, когда надо получить сущность бд по имени */
public interface BaseDtoService<DTO, Entity extends BaseEntity> {
    DTO getByName(String name);
}
