package ru.sen.searchserver.remoteService;

import ru.sen.searchserver.dto.remote.ResponseUsersDto;

/**
 * the service is designed for working with users,
 * and getting lists of users by specific names and surnames
 *
 * The methods work is organized through communication with another service
 * and receiving a response from it
 */
public interface AccountService {

    /**
     * getting all users from another service
     * @return a response from another service that contains a list of users , or an error
     */
    ResponseUsersDto getAllUsers();

    /**
     * getting all users from another service by last name
     * @param lastName last name
     * @return a response from another service that contains a list of users , or an error
     */
    ResponseUsersDto getUsersByLastName(String lastName);

    /**
     * getting all users from another service by first name
     * @param firstName first name
     * @return a response from another service that contains a list of users , or an error
     */
    ResponseUsersDto getUsersByFirstName(String firstName);

    /**
     * getting all users from another service by last and first name
     * @param lastName last name
     * @param firstName first name
     * @return a response from another service that contains a list of users , or an error
     */
    ResponseUsersDto getUsersByFirstNameAndLastName(String lastName, String firstName);
}
