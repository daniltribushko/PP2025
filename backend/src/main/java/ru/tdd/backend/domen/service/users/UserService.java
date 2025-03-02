package ru.tdd.backend.domen.service.users;

import ru.tdd.backend.domen.service.RUDDtoService;
import ru.tdd.backend.model.dto.ObjectDto;
import ru.tdd.backend.model.dto.users.UserDto;

public interface UserService extends RUDDtoService<UserDto> {
    ObjectDto existsByEmail(String email);
}
