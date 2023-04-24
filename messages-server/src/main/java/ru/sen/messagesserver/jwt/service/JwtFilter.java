package ru.sen.messagesserver.jwt.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.sen.messagesserver.jwt.entity.JwtAuthentication;
import ru.sen.messagesserver.jwt.util.JwtUtils;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("checking access to the request via filter");
        final String token = jwtProvider.getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateRefreshToken(token)) {
            log.info("the token received from the request {}", token);
            final Claims claims = jwtProvider.getRefreshClaims(token);
            final JwtAuthentication authentication = JwtUtils.generate(claims);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Add authentication in SecurityContextHolder");

        }
        filterChain.doFilter(request, response);
    }
}
