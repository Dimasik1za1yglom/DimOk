package ru.sen.accountserver.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.forms.AuthorizationDataForm;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public void setUpSecurity(AuthorizationDataForm data) {
        String emailNotEmpty = data.getEmail().strip();
        String passwordNotEmpty = data.getPassword().strip();
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(emailNotEmpty, passwordNotEmpty);
        try {
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
