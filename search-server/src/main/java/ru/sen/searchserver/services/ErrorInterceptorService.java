package ru.sen.searchserver.services;

import ru.sen.searchserver.dto.SearchRequestDto;

import java.time.LocalDateTime;

public interface ErrorInterceptorService {

    boolean checkIfAddingSearchRequestSuccessful(SearchRequestDto searchRequestDto,
                                                 LocalDateTime dateTime,
                                                 Long userId);

    boolean checkIfDeletingSearchRequestSuccessful(Long userId);
}
