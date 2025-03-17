package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tdd.backend.model.entities.vacancies.Vacancy;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    @Query(value = "SELECT max(id) FROM vacancy", nativeQuery = true)
    Optional<Long> getMaxId();
}
