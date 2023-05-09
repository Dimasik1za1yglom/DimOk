package ru.sen.searchserver.jwt.util;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.sen.searchserver.jwt.entity.JwtAuthentication;

@Slf4j
@NoArgsConstructor
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        log.info("Generate JwtAuthentication in claims: {}", claims);
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(claims.get("role", String.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }
}