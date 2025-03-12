package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.vacancies.VacancyResponse;

public interface VacancyResponseRepository extends JpaRepository<VacancyResponse, Long> {
}
