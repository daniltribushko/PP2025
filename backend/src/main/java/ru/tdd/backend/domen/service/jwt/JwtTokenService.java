package ru.tdd.backend.domen.service.jwt;

import io.jsonwebtoken.Claims;
import ru.tdd.backend.model.entities.users.User;

/** Сервис для работы с jwt токенами*/
public interface JwtTokenService {
    String createToken(User user);
    Claims parseToken(String token);
    boolean validateToken(String token, String email);
}
