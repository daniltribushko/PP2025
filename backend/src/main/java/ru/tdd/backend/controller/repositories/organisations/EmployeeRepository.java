package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.organisations.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
