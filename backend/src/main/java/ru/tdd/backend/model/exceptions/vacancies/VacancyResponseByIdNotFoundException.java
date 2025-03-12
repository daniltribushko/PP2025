package ru.tdd.backend.model.exceptions.vacancies;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class VacancyResponseByIdNotFoundException extends NotFoundException {
    public VacancyResponseByIdNotFoundException(Long id) {
        super("Отклик на вакансию с идентифкатором " + id + " не найден");
    }
}
