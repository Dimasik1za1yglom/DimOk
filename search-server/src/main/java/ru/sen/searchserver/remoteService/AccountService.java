package ru.sen.searchserver.remoteService;

import ru.sen.searchserver.dto.ResponseUsersDto;

public interface AccountService {

    ResponseUsersDto getAllUsers();

    ResponseUsersDto getUsersByLastName(String lastName);

    ResponseUsersDto getUsersByFirstName(String firstName);

    ResponseUsersDto getUsersByFirstNameAndLastName(String lastName, String firstName);
}
