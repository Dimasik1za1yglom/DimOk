package ru.sen.accountserver.remoteService;

import ru.sen.accountserver.dto.remote.ResponseDto;

/**
 * the service is used to send requests to the post service
 */
public interface PostService {

    /**
     * sends a request to delete all user posts
     *
     * @param userId id of the user whose posts should be deleted
     * @return an object of the class that can contain a successful response to an error message
     */
    ResponseDto deletePosts(Long userId);
}
