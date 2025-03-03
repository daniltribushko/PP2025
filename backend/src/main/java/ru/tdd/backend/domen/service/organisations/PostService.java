package ru.tdd.backend.domen.service.organisations;

import ru.tdd.backend.domen.service.CRUDServiceDto;
import ru.tdd.backend.domen.service.FindListDictionaryDtoService;
import ru.tdd.backend.model.dto.DictionaryDto;

/** Сервис для работы с должностями */
public interface PostService extends CRUDServiceDto<DictionaryDto>, FindListDictionaryDtoService<DictionaryDto> {
}
