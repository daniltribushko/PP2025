package ru.tdd.backend.model.entities.vacancies;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.DictionaryEntity;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.DtoEntity;

@Entity
public class Skill extends DictionaryEntity implements DtoEntity<DictionaryDto> {
    public Skill() {
        super();
    }

    @Override
    public DictionaryDto toDto() {
        return new DictionaryDto(id, name, description);
    }
}
