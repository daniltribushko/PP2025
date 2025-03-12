package ru.tdd.backend.controller.repositories.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tdd.backend.model.entities.DBFile;

public interface DBFileRepository extends JpaRepository<DBFile, Long> {
}
