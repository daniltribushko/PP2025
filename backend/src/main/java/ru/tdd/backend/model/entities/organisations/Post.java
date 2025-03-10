package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.DictionaryEntity;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.DtoEntity;

/** Сущность должности работника */
@Entity
public class Post extends DictionaryEntity implements DtoEntity<DictionaryDto> {
    public Post() {
        super();
    }


    @Override
    public DictionaryDto toDto() {
        return new DictionaryDto(id, name, description);
    }
}
