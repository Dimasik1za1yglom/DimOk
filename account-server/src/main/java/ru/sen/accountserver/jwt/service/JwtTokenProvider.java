package ru.sen.accountserver.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.jwt.util.cookie.CookieUtil;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String jwtTokenCookieName = "JWT-TOKEN";

    private final SecretKey jwtRefreshSecret;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            UserDetailsService userDetailsService) {
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.userDetailsService = userDetailsService;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getRefreshClaims(token).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String generateRefreshToken(@NonNull AuthorizationData data) {
        log.info("Generate refresh token by authorization data {}", data);
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        if (data.getUser() != null) {
            return Jwts.builder()
                    .setSubject(data.getEmail())
                    .setExpiration(refreshExpiration)
                    .signWith(jwtRefreshSecret)
                    .claim("id", data.getUser().getId())
                    .claim("role", data.getUser().getRole().getName())
                    .compact();
        } else {
            return Jwts.builder()
                    .setSubject(data.getEmail())
                    .setExpiration(refreshExpiration)
                    .signWith(jwtRefreshSecret)
                    .compact();
        }
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
            log.info("Token validate");
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
