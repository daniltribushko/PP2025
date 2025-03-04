package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tdd.backend.model.entities.organisations.Post;

import java.util.List;

@Repository
public interface PostPagingRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderById(
            String name,
            String description,
            Pageable pageable
    );

    List<Post> findAllByOrderById(Pageable pageable);
}
