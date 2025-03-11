package ru.tdd.backend.controller.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.dto.orgaisations.OrganisationDto;
import ru.tdd.backend.model.exceptions.ValidationException;

@Aspect
@Component
public class DtoServiceAspect {
    @Before(value = "@annotation(ru.tdd.backend.domen.annotations.DtoNameNotEmpty) && args(dictionaryDto)", argNames = "dictionaryDto")
    public void isDtoNameNotEmpty(DictionaryDto dictionaryDto) {
        if (dictionaryDto.getName() == null || dictionaryDto.getName().isEmpty()) {
            throw new ValidationException("Название, не должно быть пустым");
        }
    }

    @Before(value = "@annotation(ru.tdd.backend.domen.annotations.DtoNameNotEmpty) && args(dto)", argNames = "dto")
    public void isOrgNameNotEmpty(OrganisationDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new ValidationException("Название, не должно быть пустым");
        }

    }
}
