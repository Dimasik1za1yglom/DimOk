package ru.sen.searchserver.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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


    /**
     * getting all users from another service by last name
     *
     * @param lastName last name
     * @return a response from another service that contains a list of users , or an error
     */
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


    /**
     * getting all users from another service by first name
     *
     * @param firstName first name
     * @return a response from another service that contains a list of users , or an error
     */
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


    /**
     * getting all users from another service by last and first name
     *
     * @param lastName  last name
     * @param firstName first name
     * @return a response from another service that contains a list of users , or an error
     */
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
