package ru.sen.accountserver.dao;

import ru.sen.accountserver.entity.User;

import java.sql.SQLException;
import java.util.Optional;

/**
 * The interface interacts with the user in the database.
 * <p>
 * The functionality is presented:
 * - adding a user
 * - getting the user
 * - deleting a user
 * - user update
 */
public interface UserRepository {

    /**
     * Adding a user to the database
     *
     * @param user accepts an object of the User.class
     * @return primary key(id) created line
     */
    Long addUser(User user) throws SQLException;

    /**
     * get an object User.class by his id
     * @param id primary key
     * @return an Optional<User> type class. Perhaps the object will not be in the database
     */
    Optional<User> getUserById(Long id);

    /**
     * changing one or more values in one row in the database.
     * @changUser user with changed fields
     * @return true if the changes were successful and false if not
     */
    boolean updateUser(User changUser);

    /**
     * deleting a user by its primary key
     * @param id primary key user
     * @return true if the deletion was successful, false if not
     */
    boolean deleteUserById(Long id);
}
