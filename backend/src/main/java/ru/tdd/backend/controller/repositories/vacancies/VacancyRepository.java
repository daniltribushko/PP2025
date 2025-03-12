package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.vacancies.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
