package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.vacancies.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
}
