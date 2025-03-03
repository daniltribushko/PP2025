package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.organisations.OrganisationTag;

import java.util.Optional;

public interface OrganisationTagRepository extends JpaRepository<OrganisationTag, Long> {
    Optional<OrganisationTag> findByName(String name);
}
