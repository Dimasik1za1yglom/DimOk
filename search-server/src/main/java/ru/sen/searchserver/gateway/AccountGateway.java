package ru.sen.searchserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.dto.remote.ResponseUsersDto;

/**
 * the service is designed for working with users,
 * and getting lists of users by specific names and surnames
 * <p>
 * The methods work is organized through communication with another service
 * and receiving a response from it
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountGateway {

    private final WebClient.Builder webClient;


    /**
     * getting all users from another service by search request
     *
     * @param searchRequestDto contains the first and last name fields that will be searched for
     * @return a response from another service that contains a list of users , or an error
     */
    public ResponseUsersDto getUsersByFilter(SearchRequestDto searchRequestDto) {
        return webClient
                .build()
                .get()
                .uri("http://accountServer/app/users/filter",
                        uriBuilder -> uriBuilder
                                .queryParam("searchFilter", searchRequestDto)
                                .build())
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();

    }

    /**
     * getting all users from another service
     *
     * @return a response from another service that contains a list of users , or an error
     */
    public ResponseUsersDto getAllUsers() {
        return webClient
                .build()
                .get()
                .uri("http://accountServer/app/users/all")
                .retrieve()
                .bodyToMono(ResponseUsersDto.class)
                .block();
    }
}
