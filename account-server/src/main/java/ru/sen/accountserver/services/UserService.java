package ru.sen.accountserver.services;

import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.exception.UserOperationException;

/**
 * the interface is designed to implement business logic for working with user data, saving, changing data.
 * It is recommended to catch errors when working with queries in the repository to the database
 */
public interface UserService {

    /**
     * creates a user object for further storage in the database
     * and binds it to the authorization data by email
     *
     * @param userDto the object whose data should be transferred to the object User.class
     * @param email    is the primary key, user id should ,snm is linked to this mail
     * @throws UserOperationException drops out if the user failed to added
     */
    void addUser(UserDto userDto, String email) throws UserOperationException;

    /**
     * deleting an object User.class from the data table.
     * Previously, the authorization data associated with this user must be deleted.
     * Additionally, the user's deletion rights must be checked.
     * A user can delete only himself, or users with administrator rights can delete another user
     *
     * @param userToDeleteId id of the user to be deleted
     * @param email          the user who is deleting (checking for rights)
     * @throws UserOperationException drops out if the user failed to delete
     */
    void deleteUser(Long userToDeleteId, String email) throws UserOperationException;

    /**
     * getting a user by his id,
     * it is recommended to check for exceptions if there is no such user for database queries
     *
     * @param userId user id
     * @return returns an object of the class User.class ,
     * or throw an exception if such an object is not found.
     * user should not be null
     */
    User getUserById(Long userId);

    /**
     * updates the user in the database
     *
     * @param userDto  turns an object into a class object User.class
     * @param userId user id
     * @throws UserOperationException drops out if the user failed to update
     */
    void updateUser(UserDto userDto, Long userId) throws UserOperationException;
}
