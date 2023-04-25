package ru.sen.accountserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.User;

import java.util.List;

/**
 * The interface interacts with the user in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * sends a request to the database to get a list of users by first name
     *
     * @param firstName firstName
     * @return list of user
     */
    List<User> findAllByFirstName(String firstName);

    /**
     * sends a request to the database to get a list of users by last name
     *
     * @param lastName lastName
     * @return list of user
     */
    List<User> findAllByLastName(String lastName);

    /**
     * sends a request to the database to get a list of users by first name and last name
     *
     * @param firstName firstName
     * @param lastName  lastName
     * @return list of user
     */
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);

    /**
     * request to the database for the presence of a mobile number from at least one user
     *
     * @param phone mobile number
     * @return true if there is, and false if there is not
     */
    boolean existsByPhone(String phone);
}
