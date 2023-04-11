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
        return null;
    }

    @Override
    public ResponseUsersDto getUsersByLastName(String lastName) {
        return null;
    }

    @Override
    public ResponseUsersDto getUsersByFirstName(String firstName) {
        return null;
    }

    @Override
    public ResponseUsersDto getUsersByFirstNameAndLastName(String lastName, String firstName) {
        return null;
    }
}
