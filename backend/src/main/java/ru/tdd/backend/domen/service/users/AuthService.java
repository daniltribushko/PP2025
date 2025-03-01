package ru.tdd.backend.domen.service.users;

import ru.tdd.backend.model.dto.users.JwtToken;
import ru.tdd.backend.model.dto.users.SignIn;
import ru.tdd.backend.model.dto.users.SignUp;

/** Сервис для авторизации и регистрации пользователей*/
public interface AuthService {
    JwtToken signUp(SignUp signUp);
    JwtToken signIn(SignIn signIn);
}
