package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tdd.backend.model.entities.organisations.OrganisationTag;

import java.util.List;

public interface OrganisationTagPagingRepository extends PagingAndSortingRepository<OrganisationTag, Long> {
    List<OrganisationTag> findAllByOrderById(Pageable pageable);
    List<OrganisationTag> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderById(
            String name,
            String description,
            Pageable pageable
    );
}
