package ru.tdd.backend.domen.service.users;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import ru.tdd.backend.domen.service.RUDDtoService;
import ru.tdd.backend.model.dto.ObjectDto;
import ru.tdd.backend.model.dto.users.UserDto;

import java.util.List;

public interface UserService extends RUDDtoService<UserDto> {
    ObjectDto existsByEmail(String email);
    List<UserDto> findAll(
            @NotBlank(message = "Наименование роли не должно быть пустым")
            String role,
            @Min(value = 0, message = "Номер страницы должен быть больше либо равен 0")
            Integer page,
            @Min(value = 1, message = "Количество записей на странице должно быть больше 1")
            Integer perPage
    );
}
