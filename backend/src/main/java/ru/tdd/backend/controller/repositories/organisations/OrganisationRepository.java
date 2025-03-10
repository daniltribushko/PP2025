package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.organisations.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    boolean existsByTitle(String title);
}
