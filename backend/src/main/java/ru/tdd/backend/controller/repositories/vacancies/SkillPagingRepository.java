package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tdd.backend.model.entities.vacancies.Skill;

import java.util.List;

public interface SkillPagingRepository extends PagingAndSortingRepository<Skill, Long> {
    List<Skill> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description,
            Pageable pageable
    );
}
