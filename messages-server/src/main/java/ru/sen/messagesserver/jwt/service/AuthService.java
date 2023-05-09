package ru.sen.messagesserver.jwt.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sen.messagesserver.jwt.exception.AuthException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public Long getIdUserByRefreshToken(HttpServletRequest request) throws AuthException {
        final String token = jwtTokenProvider.getTokenFromRequest(request);
        if (token != null) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(token);
            final Long userId = claims.get("id", Long.class);
            log.info("Getting the userId from the token was successful. UserId: {}", userId);
            return userId;
        }
        throw new AuthException("Отсутсвует JWT токен");
    }

    public String getLastNameByRefreshToken(HttpServletRequest request) throws AuthException {
        final String token = jwtTokenProvider.getTokenFromRequest(request);
        if (token != null) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(token);
            final String lastName = claims.get("lastName", String.class);
            log.info("Getting the lastName from the token was successful. lastName: {}", lastName);
            return lastName;
        }
        throw new AuthException("Отсутсвует JWT токен");
    }

    public String getFirstNameByRefreshToken(HttpServletRequest request) throws AuthException {
        final String token = jwtTokenProvider.getTokenFromRequest(request);
        if (token != null) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(token);
            final String firstName = claims.get("firstName", String.class);
            log.info("Getting the firstName from the token was successful. FirstName: {}", firstName);
            return firstName;
        }
        throw new AuthException("Отсутсвует JWT токен");
    }
}
