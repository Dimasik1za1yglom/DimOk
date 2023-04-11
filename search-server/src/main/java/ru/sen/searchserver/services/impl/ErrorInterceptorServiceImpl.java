package ru.sen.searchserver.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.exception.SearchRequestException;
import ru.sen.searchserver.services.ErrorInterceptorService;
import ru.sen.searchserver.services.SearchRequestService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final SearchRequestService requestService;

    @Override
    public boolean checkIfAddingSearchRequestSuccessful(SearchRequestDto searchRequestDto,
                                                        LocalDateTime dateTime,
                                                        Long userId) {
        try {
            requestService.addSearchRequest(searchRequestDto, dateTime, userId);
            return true;
        } catch (SearchRequestException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletingSearchRequestSuccessful(Long userId) {
        try {
            requestService.deleteSearchRequestsByUserId(userId);
            return true;
        } catch (SearchRequestException e) {
            return false;
        }
    }
}
