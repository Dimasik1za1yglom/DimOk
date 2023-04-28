package ru.sen.accountserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDto;

/**
 * the service is used to send requests to the dialog service
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class DialogGateway {

    private final WebClient.Builder webClient;

    public ResponseDto existsDialog(Long createUserId, Long userId) {
        return webClient
                .build()
                .get()
                .uri("http://messagesServer/app/dialog/exists",
                        uriBuilder -> uriBuilder
                                .queryParam("createUserId", createUserId)
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
