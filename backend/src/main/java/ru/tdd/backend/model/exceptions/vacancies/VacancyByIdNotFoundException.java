package ru.tdd.backend.model.exceptions.vacancies;

import ru.tdd.backend.model.exceptions.NotFoundException;

public class VacancyByIdNotFoundException extends NotFoundException {
    public VacancyByIdNotFoundException(Long id) {
        super("Вакансия с идентификатором " + id + " не найдена");
    }
}
