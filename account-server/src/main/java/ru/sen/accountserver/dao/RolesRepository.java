package ru.sen.accountserver.dao;

import ru.sen.accountserver.entity.Role;

import java.util.Optional;

/**
 * the interface is designed to work with the roles table in the database
 */
public interface RolesRepository {

    /**
     * getting a class object Role.class, by his id
     * @param id primary key
     * @return an object of the Optional<Role> class, since there may be a non-existent role
     */
    Optional<Role> getRoleById(Long id);
}
