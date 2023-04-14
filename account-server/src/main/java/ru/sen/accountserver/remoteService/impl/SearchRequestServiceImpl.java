package ru.sen.accountserver.remoteService.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseSearchRequestDto;
import ru.sen.accountserver.remoteService.SearchRequestService;

@Service
@RequiredArgsConstructor
public class SearchRequestServiceImpl implements SearchRequestService {

    private final WebClient webClient;

    @Override
    public ResponseSearchRequestDto deleteSearchRequest(Long userId) {
        return webClient
                .post()
                .uri("http://searchServer/searchRequest/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseSearchRequestDto.class)
                .block();
    }
}
