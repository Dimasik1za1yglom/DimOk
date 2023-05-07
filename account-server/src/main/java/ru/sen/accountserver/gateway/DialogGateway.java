package ru.sen.accountserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDialogDto;
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

    public ResponseDialogDto existsDialog(Long createUserId, Long userId) {
        return webClient
                .build()
                .get()
                .uri("http://messagesServer/app/dialog/exists",
                        uriBuilder -> uriBuilder
                                .queryParam("createUserId", createUserId)
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDialogDto.class)
                .block();
    }

    public ResponseDto deleteDialogsByUser(Long userId) {
        return webClient
                .build()
                .post()
                .uri("http://messagesServer/app/dialog/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }

    public ResponseDto changeDialogNameLinkedByUser(String newNameDialog, Long userId) {
        return webClient
                .build()
                .post()
                .uri("http://messagesServer/app/dialog/change",
                        uriBuilder -> uriBuilder
                                .queryParam("newNameDialog", newNameDialog)
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
