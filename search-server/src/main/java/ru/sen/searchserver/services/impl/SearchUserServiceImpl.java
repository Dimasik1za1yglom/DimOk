package ru.sen.searchserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sen.searchserver.dto.ResponseUsersDto;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.exception.SearchUsersException;
import ru.sen.searchserver.remoteService.AccountService;
import ru.sen.searchserver.services.SearchUserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchUserServiceImpl implements SearchUserService {

    private final AccountService accountService;

    @Override
    public List<User> getAllUsersByTextRequest(SearchRequestDto searchRequestDto) throws SearchUsersException {
        log.info("Getting users Search Request by sending a request to the account service");
        ResponseUsersDto response;
        if (searchRequestDto.getFirstName() == null) {
            log.info("User search bar has only the last name: {}", searchRequestDto.getLastName());
            response = accountService.getUsersByLastName(searchRequestDto.getLastName());
        } else if (searchRequestDto.getLastName() == null) {
            log.info("User search bar has only the last name: {}", searchRequestDto.getFirstName());
            response = accountService.getUsersByFirstName(searchRequestDto.getFirstName());
        } else {
            log.info("In the search bar user has a last name with a first name: {}", searchRequestDto);
            response = accountService.
                    getUsersByFirstNameAndLastName(searchRequestDto.getLastName(),
                            searchRequestDto.getFirstName());
        }
        if (!response.isSuccess()) {
            log.error("Failed to get a response from account service. There is a error: {}", response.getMessage());
            throw new SearchUsersException(response.getMessage());
        }
        log.info("Successful to get a response from account service");
        return response.getUsers();
    }

    @Override
    public List<User> getAllUsers() throws SearchUsersException {
        log.info("Getting all users by sending a request to the account service");
        ResponseUsersDto response = accountService.getAllUsers();
        if (!response.isSuccess()) {
            log.error("Failed to get a response from account service. There is a error: {}", response.getMessage());
            throw new SearchUsersException(response.getMessage());
        }
        log.info("Successful to get a response from account service");
        return response.getUsers();
    }
}
