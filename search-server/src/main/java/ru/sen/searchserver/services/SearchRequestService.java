package ru.sen.searchserver.services;

import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.SearchRequest;
import ru.sen.searchserver.exception.SearchRequestException;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchRequestService {

    void addSearchRequest(SearchRequestDto searchRequestDto, LocalDateTime dateTime, Long userId)
            throws SearchRequestException;

    void deleteSearchRequestsByUserId(Long userId)
            throws SearchRequestException;

    List<SearchRequest> getAllSearchRequestUsers(Long userId);
}
