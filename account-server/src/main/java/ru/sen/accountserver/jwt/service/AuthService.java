package ru.sen.accountserver.jwt.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dto.AuthorizationDataDto;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.jwt.entity.JwtResponse;
import ru.sen.accountserver.jwt.exception.AuthException;
import ru.sen.accountserver.repository.AuthorizationDataRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationDataRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponse login(@NonNull AuthorizationDataDto authorizationDataDto) throws AuthException {
        final AuthorizationData data = dataRepository.findById(authorizationDataDto.getEmail()).
                orElseThrow(() -> new AuthException("Почты такой не существует, попробуйте зарегестрироваться"));
        if (data.getPassword().equals(passwordEncoder.encode(authorizationDataDto.getPassword()))) {
            final String refreshToken = jwtTokenProvider.generateRefreshToken(data);
            refreshStorage.put(data.getEmail(), refreshToken);
            return new JwtResponse(refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getRefresh(@NonNull String refreshToken) throws AuthException {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final AuthorizationData data = dataRepository.findById(email)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String newRefreshToken = jwtTokenProvider.generateRefreshToken(data);
                refreshStorage.put(data.getEmail(), newRefreshToken);
                return new JwtResponse(newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtResponse getRefresh(HttpServletRequest request) throws AuthException {
        final String token = jwtTokenProvider.getTokenFromRequest(request);
        if (token != null) {
            return getRefresh(token);
        }
        throw new AuthException("Отсутсвует JWT токен");
    }

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
}
