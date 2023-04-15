package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.User;

import java.util.List;

/**
 * the service is needed to search for users by the specified parameters
 */
public interface UsersSearchService {

    /**
     * returns a list of all users
     *
     * @return list of user
     */
    List<User> getAllUsers();

    /**
     * returns a list of users by last name
     *
     * @param lastName last name
     * @return list of user
     */
    List<User> getUsersByLastName(String lastName);

    /**
     * returns a list of users by first name
     *
     * @param firstName firstName
     * @return list of user
     */
    List<User> getUsersByFirstName(String firstName);

    /**
     * returns a list of users by first name and last name
     *
     * @param lastName lastName
     * @param firstName firstName
     * @return list of user
     */
    List<User> getUsersByFirstNameAndLastName(String lastName, String firstName);

}
