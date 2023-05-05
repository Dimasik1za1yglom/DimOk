package ru.sen.messagesserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.messagesserver.dto.remote.ResponseUserDto;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountGateway {

    private final WebClient.Builder webClient;

    /**
     * getting user name from another service by userId
     *
     * @param userId user Id
     * @return a response from another service that contains user name , or an error
     */
    public ResponseUserDto getUserNameByUserId(Long userId) {
        return webClient
                .build()
                .get()
                .uri("http://accountServer/app/user/id",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUserDto.class)
                .block();
    }
}
