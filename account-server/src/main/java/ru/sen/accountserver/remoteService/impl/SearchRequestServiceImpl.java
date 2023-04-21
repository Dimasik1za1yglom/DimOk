package ru.sen.accountserver.remoteService.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDto;
import ru.sen.accountserver.remoteService.SearchRequestService;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRequestServiceImpl implements SearchRequestService {

    private final WebClient.Builder webClient;

    @Override
    public ResponseDto deleteSearchRequest(Long userId) {
        return webClient
                .build()
                .post()
                .uri("http://searchServer/app/searchRequests/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
