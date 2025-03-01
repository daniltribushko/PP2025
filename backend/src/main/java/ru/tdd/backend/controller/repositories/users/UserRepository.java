package ru.tdd.backend.controller.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.users.User;

import java.util.Optional;

/** Репозиторий для работы с пользователями*/
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
