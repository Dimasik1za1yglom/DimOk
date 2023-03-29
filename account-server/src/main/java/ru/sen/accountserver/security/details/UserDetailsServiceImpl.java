package ru.sen.accountserver.security.details;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.repository.AuthorizationDataRepository;
import ru.sen.accountserver.repository.RolesRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.Role;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthorizationDataRepository dataRepository;
    private final RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("spring security: getting user login data");
        AuthorizationData data = dataRepository.findById(username).
                orElseThrow(() -> new UsernameNotFoundException("AuthorizationData not found"));
        log.info("spring security: obtaining user authorization data: {}", data);
        Long roleId = 1L;
        if (data.getUser() != null) {
            roleId = data.getUser().getRole().getId();
        }
        log.info("spring security: the final value of the user roleId: {}", roleId);
        Role role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new UsernameNotFoundException("role not found"));
        log.info("spring security: getting a user role: {}", role.getName());
        return new UserDetailsImpl(data, true, role);
    }
}
