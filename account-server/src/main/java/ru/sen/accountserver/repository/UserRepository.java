package ru.sen.accountserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.User;

import java.util.List;

/**
 * The interface interacts with the user in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByFirstName(String firstName);

    List<User> findAllByLastName(String lastName);

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
}
