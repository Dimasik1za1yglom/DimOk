package ru.sen.searchserver.services;

import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.SearchRequest;
import ru.sen.searchserver.exception.SearchRequestException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * service for working with search query histories of other users
 */
public interface SearchRequestService {


    /**
     * checking that search request by users exist
     * @param userId id of the user who  the request
     * @return true if the requests exist, fasle if there are no such requests
     */
    boolean checkIfSearchRequestExists(Long userId);

    /**
     * adds a search query
     * @param searchRequestDto what data was transmitted by the user
     * @param dateTime time when the search request was created
     * @param userId id of the user who created the request
     * @throws SearchRequestException the error pops up if the transaction failed after adding
     * the query to the database
     */
    void addSearchRequest(SearchRequestDto searchRequestDto, LocalDateTime dateTime, Long userId)
            throws SearchRequestException;

    /**
     * deleting all requests of a specific user
     * @param userId id of the user who deleted the request
     * @throws SearchRequestException the error pops up if the transaction failed after deleting
     * the query to the database
     */
    void deleteSearchRequestsByUserId(Long userId)
            throws SearchRequestException;

    /**
     * getting all the requests of a specific user
     * @param userId id of the user the request
     * @return list of search queries
     */
    List<SearchRequest> getAllSearchRequestByUserId(Long userId);
}
