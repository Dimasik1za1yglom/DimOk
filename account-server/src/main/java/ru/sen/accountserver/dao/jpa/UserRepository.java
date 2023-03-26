package ru.sen.accountserver.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.User;

/**
 * The interface interacts with the user in the database.
 * <p>
 * The functionality is presented:
 * - adding a user
 * - getting the user
 * - deleting a user
 * - user update
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
