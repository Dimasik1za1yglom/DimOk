package ru.sen.accountserver.dao;

import ru.sen.accountserver.entity.Role;

import java.util.Optional;

public interface RolesRepository {

    Optional<Role> getRoleById(Long id);
}
