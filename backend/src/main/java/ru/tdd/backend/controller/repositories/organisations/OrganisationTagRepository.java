package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.organisations.OrganisationTag;

public interface OrganisationTagRepository extends JpaRepository<OrganisationTag, Long> {
    boolean existsByName(String name);
}
