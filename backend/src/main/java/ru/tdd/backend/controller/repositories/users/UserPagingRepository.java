package ru.tdd.backend.controller.repositories.users;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tdd.backend.model.entities.users.User;

import java.util.List;

public interface UserPagingRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findAllByOrderById(Pageable pageable);
}
