package ru.sen.searchserver.remoteService.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.searchserver.dto.ResponseUsersDto;
import ru.sen.searchserver.remoteService.AccountService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final WebClient webClient;

    @Override
    public ResponseUsersDto getAllUsers() {
        return webClient
                .get()
                .uri("http://accountServer/users/all")
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }

    @Override
    public ResponseUsersDto getUsersByLastName(String lastName) {
        return webClient
                .get()
                .uri("http://accountServer/users/lastName",
                        uriBuilder -> uriBuilder
                                .queryParam("lastName", lastName)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }

    @Override
    public ResponseUsersDto getUsersByFirstName(String firstName) {
        return webClient
                .get()
                .uri("http://accountServer/users/firstName",
                        uriBuilder -> uriBuilder
                                .queryParam("firstName", firstName)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }

    @Override
    public ResponseUsersDto getUsersByFirstNameAndLastName(String lastName, String firstName) {
        return webClient
                .get()
                .uri("http://accountServer/users/fullName",
                        uriBuilder -> uriBuilder
                                .queryParam("lastName", lastName)
                                .queryParam("firstName", firstName)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }
}
