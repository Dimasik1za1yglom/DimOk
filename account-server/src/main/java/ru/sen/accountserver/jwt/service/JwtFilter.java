package ru.sen.accountserver.jwt.service;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtProvider;
    private final List<String> ignorePaths = List.of("/registration", "/input", "/app/**");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        log.info("checking access to the request via filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();
        log.info("getting a path from a request:{}", url);
        for (String path : ignorePaths) {
            if (url.contains(path)) {
                log.info("the request contains an ignored path: {}", path);
                fc.doFilter(request, response);
                return;
            }
        }
        log.info("the request does not contain ignored paths");
        final String token = jwtProvider.getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateRefreshToken(token)) {
            log.info("the token received from the request {}", token);
            final Authentication authentication = jwtProvider.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Add authentication in SecurityContextHolder");
            }
        }
        fc.doFilter(request, response);
    }
}
