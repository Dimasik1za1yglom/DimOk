package ru.sen.accountserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.User;

/**
 * The interface interacts with the user in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
