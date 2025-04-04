package ru.tdd.backend.model.entities.organisations;

import jakarta.persistence.Entity;
import ru.tdd.backend.model.DictionaryEntity;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.DtoEntity;

/** Тэг организации */
@Entity
public class OrganisationTag extends DictionaryEntity implements DtoEntity<DictionaryDto> {
    public OrganisationTag(String name, String description) {
        super(name, description);
    }

    public OrganisationTag() {

    }

    @Override
    public DictionaryDto toDto() {
        return new DictionaryDto(id, name, description);
    }
}
