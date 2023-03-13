package ru.sen.accountserver.dao;

import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;

import java.util.Optional;

/**
 * The interface interacts with the user in the database.
 * <p>
 * Contains methods for working with the object AuthorizationData.class:
 * - delete object
 * - added object
 * - getting object
 */
public interface AuthorizationDataRepository {

    /**
     * adds authorization data to the database.
     * The password field in the object AuthorizationData.class should be encrypted for greater reliability
     * @param data accepts an object of the AuthorizationData.class
     * @return true if it was possible to save in the database.
     * false if it was not possible to save in the database
     */
    boolean addAuthorizationData(AuthorizationData data);

    /**
     * tries to return an object of the class AuthorizationData.class by email primary key
     * @param email is the primary key in the table
     * @return an Optional<AuthorizationData> type class. Perhaps the object will not be in the database
     */
    Optional<AuthorizationData> getDataByEmail(String email);

    /**
     * Deletes a string by userId
     * @param userId user id from the users table
     * @return true if the deletion was successful, false if not
     */
    boolean deleteDataByUserId(Long userId);

    /**
     * Updating user data
     * @param data an object with new updated fields
     * @return true if it was possible to change in the database.
     * false if it was not possible to change in the database
     */
    boolean updateData(AuthorizationData data);
}
