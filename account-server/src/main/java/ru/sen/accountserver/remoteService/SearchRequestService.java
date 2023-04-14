package ru.sen.accountserver.remoteService;


import ru.sen.accountserver.dto.remote.ResponseSearchRequestDto;

public interface SearchRequestService {

    ResponseSearchRequestDto deleteSearchRequest(Long userId);
}
