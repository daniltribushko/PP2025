package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tdd.backend.model.entities.vacancies.Vacancy;

public interface VacancyPagingRepository extends PagingAndSortingRepository<Vacancy, Long> {
}
