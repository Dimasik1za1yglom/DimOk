package ru.sen.accountserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.Role;

/**
 * the interface is designed to work with the roles table in the database
 */
public interface RolesRepository extends JpaRepository<Role, Long> {

}
