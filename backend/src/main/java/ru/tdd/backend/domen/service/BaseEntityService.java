package ru.tdd.backend.domen.service;

import ru.tdd.backend.model.BaseEntity;

import java.util.Optional;

/** Сервис для работы с сущностями, когда надо получить сущность бд по имени*/
public interface BaseEntityService<Entity extends BaseEntity> {
    Entity findByName(String name);
}
