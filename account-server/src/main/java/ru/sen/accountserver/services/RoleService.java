package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.Role;

/**
 * the interface is designed to work with user roles
 */
public interface RoleService {

    /**
     * getting a role by id
     *
     * @param roleId role id
     * @return object Role.class
     */
    Role getRoleById(Long roleId);
}
