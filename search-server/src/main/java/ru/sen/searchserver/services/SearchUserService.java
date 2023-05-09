package ru.sen.searchserver.services;

import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.exception.SearchUsersException;

import java.util.List;

/**
 * the interface is needed to work with the text of user requests to search for other users
 * the implementation of the methods should work through communication with other services.
 */
public interface SearchUserService {

    /**
     * getting all users who match by first name or last name through another service
     *
     * @param searchRequestDto contains a first or last name
     * @return list of users that match the query string
     * @throws SearchUsersException the error occurs when an unsuccessful response from another service
     */
    List<User> getAllUsersByTextRequest(SearchRequestDto searchRequestDto) throws SearchUsersException;

    /**
     * getting all users through another service
     *
     * @return list of all users
     * @throws SearchUsersException the error occurs when an unsuccessful response from another servic
     */
    List<User> getAllUsers() throws SearchUsersException;

}
