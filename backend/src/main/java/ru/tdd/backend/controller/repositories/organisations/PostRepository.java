package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tdd.backend.model.entities.organisations.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByName(String name);
}
