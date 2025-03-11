package ru.tdd.backend.domen.service.vacancies;

import ru.tdd.backend.domen.service.CRUDServiceDto;
import ru.tdd.backend.domen.service.FindListDictionaryDtoService;
import ru.tdd.backend.model.dto.DictionaryDto;

public interface SkillService extends CRUDServiceDto<DictionaryDto>, FindListDictionaryDtoService<DictionaryDto> {
}
