package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tdd.backend.model.entities.organisations.Organisation;

import java.util.List;

public interface OrganisationPagingRepository extends PagingAndSortingRepository<Organisation, Long> {
}
