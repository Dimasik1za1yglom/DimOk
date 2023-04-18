package ru.sen.searchserver.jwt.util;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import ru.sen.searchserver.jwt.entity.JwtAuthentication;

@NoArgsConstructor
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(claims.get("role", String.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }
}