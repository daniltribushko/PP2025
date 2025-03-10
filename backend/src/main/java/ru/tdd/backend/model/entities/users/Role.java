package ru.tdd.backend.model.entities.users;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.BaseEntity;
import ru.tdd.backend.model.dto.BaseDto;
import ru.tdd.backend.model.dto.DtoEntity;

/** Клас роли пользователя */
@Entity
public class Role extends BaseEntity implements DtoEntity<BaseDto> {
    public Role(String name) {
        super(name);
    }

    public Role() {
        super();
    }

    @Override
    public BaseDto toDto() {
        return new BaseDto(name);
    }
}
