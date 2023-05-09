package ru.sen.searchserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.dto.remote.ResponseUsersDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.exception.SearchUsersException;
import ru.sen.searchserver.gateway.AccountGateway;
import ru.sen.searchserver.services.SearchUserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchUserServiceImpl implements SearchUserService {

    private final AccountGateway accountGateway;

    @Override
    public List<User> getAllUsersByTextRequest(SearchRequestDto searchRequestDto) throws SearchUsersException {
        log.info("Getting users Search Request by sending a request to the account service");
        ResponseUsersDto response = accountGateway.getUsersByFilter(searchRequestDto);
        log.info("getting a response from a search service: {}", response);
        if (!response.isSuccess()) {
            log.error("Failed to get a response from account service. There is a error: {}", response.getMessage());
            throw new SearchUsersException("Не удалось получить пользователей по данному запросу," +
                    " повторите поиск позднее");
        }
        log.info("Successful to get a response from account service");
        return response.getUsers();
    }

    @Override
    public List<User> getAllUsers() throws SearchUsersException {
        log.info("Getting all users by sending a request to the account service");
        ResponseUsersDto response = accountGateway.getAllUsers();
        if (!response.isSuccess()) {
            log.error("Failed to get a response from account service. There is a error: {}", response.getMessage());
            throw new SearchUsersException(response.getMessage());
        }
        log.info("Successful to get a response from account service");
        return response.getUsers();
    }
}
