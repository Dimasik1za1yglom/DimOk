package ru.sen.accountserver.remoteService;

import ru.sen.accountserver.dto.remote.ResponseDto;

public interface SearchRequestService {

    ResponseDto deleteSearchRequest(Long userId);
}
