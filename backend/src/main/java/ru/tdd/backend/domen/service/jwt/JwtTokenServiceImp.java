package ru.tdd.backend.domen.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.tdd.backend.model.entities.users.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtTokenServiceImp implements JwtTokenService {
    @Value("${token.secret}")
    private String secret;

    @Override
    public String createToken(User user) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put(
                "roles",
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                .toList()
        );
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .claims(claims)
                .expiration(new Date(now.getTime() + 3600 * 1000))
                .signWith(getSecret())
                .compact();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean validateToken(String token, String email) {
        Claims claims = parseToken(token);
        return new Date().before(claims.getExpiration()) &&
                Objects.equals(claims.getSubject(), email);
    }

    private SecretKey getSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
