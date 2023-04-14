package ru.sen.accountserver.remoteService.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.accountserver.dto.remote.ResponseDto;
import ru.sen.accountserver.remoteService.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final WebClient webClient;

    @Override
    public ResponseDto deletePosts(Long userId) {
        return webClient
                .post()
                .uri("http://postServer/posts/delete",
                        uriBuilder -> uriBuilder
                                .queryParam("userId", userId)
                                .build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
