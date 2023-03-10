package ru.sen.accountserver.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final AuthorizationData authorizationData;
    private final boolean isActive;
    private final List<SimpleGrantedAuthority> authorities;


    public UserDetailsImpl(AuthorizationData authorizationData, boolean isActive, Role role) {
        this.authorizationData = authorizationData;
        this.isActive = isActive;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return authorizationData.getPassword();
    }

    @Override
    public String getUsername() {
        return authorizationData.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
