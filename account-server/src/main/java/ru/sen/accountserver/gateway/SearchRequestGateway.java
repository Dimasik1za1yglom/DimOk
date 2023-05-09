package ru.sen.accountserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDto;

/**
 * the service is used to send requests to the Search Request service
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRequestGateway {

    private final WebClient.Builder webClient;

    /**
     * sends a request to delete all user search request
     *
     * @param userId id of the user whose search request should be deleted
     * @return an object of the class that can contain a successful response to an error message
     */
    public ResponseDto deleteSearchRequest(Long userId) {
        return webClient
                .build()
                .post()
                .uri("http://searchServer/app/searchRequest/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
