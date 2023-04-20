package ru.sen.searchserver.remoteService.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.searchserver.dto.remote.ResponseUsersDto;
import ru.sen.searchserver.remoteService.AccountService;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final WebClient.Builder webClient;

    @Override
    public ResponseUsersDto getAllUsers() {
        return webClient
                .build()
                .get()
                .uri("http://accountServer/app/users/all")
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }

    @Override
    public ResponseUsersDto getUsersByLastName(String lastName) {
        return webClient
                .build()
                .get()
                .uri("http://accountServer/app/users/lastName",
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
                .build()
                .get()
                .uri("http://accountServer/app/users/firstName",
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
                .build()
                .get()
                .uri("http://accountServer/app/users/fullName",
                        uriBuilder -> uriBuilder
                                .queryParam("lastName", lastName)
                                .queryParam("firstName", firstName)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }
}
