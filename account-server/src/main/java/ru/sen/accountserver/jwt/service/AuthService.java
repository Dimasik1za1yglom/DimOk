package ru.sen.accountserver.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponse login(@NonNull AuthorizationDataDto authorizationDataDto) {
        final AuthorizationData data = dataRepository.findById(authorizationDataDto.getEmail()).
                orElseThrow(() -> new AuthException("AuthorizationData not found"));
        //TODO: КОДИРОВКА ПАРОЛЯ
        if (data.getPassword().equals(authorizationDataDto.getPassword())) {
            final String accessToken = jwtTokenProvider.generateAccessToken(data);
            final String refreshToken = jwtTokenProvider.generateRefreshToken(data);
            refreshStorage.put(data.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final AuthorizationData data = dataRepository.findById(email)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtTokenProvider.generateAccessToken(data);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final AuthorizationData data = dataRepository.findById(email)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtTokenProvider.generateAccessToken(data);
                final String newRefreshToken = jwtTokenProvider.generateRefreshToken(data);
                refreshStorage.put(data.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }
}
