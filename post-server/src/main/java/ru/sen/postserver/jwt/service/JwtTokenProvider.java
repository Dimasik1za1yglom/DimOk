package ru.sen.postserver.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.sen.postserver.jwt.util.cookie.CookieUtil;

import javax.crypto.SecretKey;
import java.security.Key;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String jwtTokenCookieName = "JWT-TOKEN";

    private final SecretKey jwtRefreshSecret;

    public JwtTokenProvider(
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));

    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        log.info("Check validate refresh token");
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        log.info("verification of token validation by secret key");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return CookieUtil.getValue(request, jwtTokenCookieName);
    }
}
