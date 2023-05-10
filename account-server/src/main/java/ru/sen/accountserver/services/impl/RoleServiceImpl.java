package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.accountserver.entity.Role;
import ru.sen.accountserver.repository.RoleRepository;
import ru.sen.accountserver.services.RoleService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long roleId) {
        log.info("getting a role by id {}", roleId);
        return roleRepository.getReferenceById(roleId);
    }
}
