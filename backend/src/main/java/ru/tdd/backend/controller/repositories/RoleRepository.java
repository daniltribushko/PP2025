package ru.tdd.backend.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.users.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
