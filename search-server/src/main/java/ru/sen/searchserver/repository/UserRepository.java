package ru.sen.searchserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.searchserver.entity.User;

import java.util.List;

/**
 * The interface interacts with the user in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByFirstName(String firstName);

    List<User> findUsersByLastName(String lastName);

    List<User> findUsersByFirstNameAndLastName(String firstName, String lastName);
}
