package ru.sen.messagesserver.jwt.util;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import ru.sen.messagesserver.jwt.entity.JwtAuthentication;

@NoArgsConstructor
public class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(claims.get("role", String.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }
}
