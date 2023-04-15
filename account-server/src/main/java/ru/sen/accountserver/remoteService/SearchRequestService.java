package ru.sen.accountserver.remoteService;

import ru.sen.accountserver.dto.remote.ResponseDto;

/**
 * the service is used to send requests to the Search Request service
 */
public interface SearchRequestService {

    /**
     * sends a request to delete all user search request
     *
     * @param userId id of the user whose search request should be deleted
     * @return an object of the class that can contain a successful response to an error message
     */
    ResponseDto deleteSearchRequest(Long userId);
}
