package ru.tdd.backend.controller.repositories.organisations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.tdd.backend.model.entities.organisations.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO employees(id, manage_organisation) VALUES(?1, ?2)", nativeQuery = true)
    void createEmployee(Long userId, Long orgId);
}
