package ru.sen.searchserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.SearchRequest;
import ru.sen.searchserver.exception.SearchRequestException;
import ru.sen.searchserver.mappers.SearchRequestMapper;
import ru.sen.searchserver.repository.SearchRequestRepository;
import ru.sen.searchserver.services.SearchRequestService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchRequestServiceImpl implements SearchRequestService {

    private final SearchRequestRepository requestRepository;
    private final SearchRequestMapper requestMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = SearchRequestException.class)
    public void addSearchRequest(SearchRequestDto searchRequestDto, LocalDateTime dateTime, Long userId)
            throws SearchRequestException {
        log.info("add search request: {} by userId: {}", searchRequestDto, userId);
        try {
            requestRepository.save(requestMapper.searchRequestDtoToSearchRequest(searchRequestDto, dateTime, userId));
            log.info("Adding a new search request by userId {} was successful", userId);
        } catch (Exception e) {
            log.error("Adding a new search request by userId {} is failed: {}", userId, e.getMessage());
            throw new SearchRequestException("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = SearchRequestException.class)
    public void deleteSearchRequestsByUserId(Long userId) throws SearchRequestException {
        log.info("Delete all search request by userId {}", userId);
        try {
            requestRepository.deleteByUserId(userId);
            log.info("Deleting all search request by id {} was successful", userId);
        } catch (Exception e) {
            log.error("Deleting all search request by id {} is failed: {}", userId, e.getMessage());
            throw new SearchRequestException("Throwing exception for demoing rollback");
        }
    }

    @Override
    public List<SearchRequest> getAllSearchRequestUsers(Long userId) {
        log.info("get all search request by userId {}", userId);
        return requestRepository.findAllByUserId(userId);
    }
}
