package ru.sen.accountserver.remoteService.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDto;
import ru.sen.accountserver.remoteService.PostService;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final WebClient.Builder webClient;

    @Override
    public ResponseDto deletePosts(Long userId) {
        return webClient
                .build()
                .post()
                .uri("http://postServer/app/posts/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
