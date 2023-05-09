package ru.sen.accountserver.services;

import ru.sen.accountserver.dto.remote.SearchRequestDto;
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
     * returns a list of users by search filter
     *
     * @param searchFilter first name and last name
     * @return list of user
     */
    List<User> getUsersBySearchFilter(SearchRequestDto searchFilter);

}
