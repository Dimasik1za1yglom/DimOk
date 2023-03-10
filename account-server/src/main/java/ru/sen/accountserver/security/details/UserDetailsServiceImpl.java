package ru.sen.accountserver.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dao.AuthorizationDataRepository;
import ru.sen.accountserver.dao.RolesRepository;
import ru.sen.accountserver.dao.UserRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.Role;
import ru.sen.accountserver.entity.User;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthorizationDataRepository dataRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthorizationData data = dataRepository.getDataByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("AuthorizationData not found"));
        User user = userRepository.getUserById(data.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Role role = rolesRepository.getRoleById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("role not found"));
        return new UserDetailsImpl(data, true, role);
    }
}
