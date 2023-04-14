package ru.sen.accountserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.accountserver.controller.api.RemoteApi;
import ru.sen.accountserver.dto.ResponseUsersDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.services.UsersSearchService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RemoteSearchController implements RemoteApi {

    private final UsersSearchService usersSearchService;

    @Override
    public ResponseUsersDto getAllUsers() {
        try {
            List<User> users = usersSearchService.getAllUsers();
            log.info("Get all users was successful. Users: {}", users);
            return new ResponseUsersDto(users, true, null);
        } catch (EntityNotFoundException e) {
            log.error("Get all users is failed: {}", e.getMessage());
            String error = "Не удалось получить всех пользователей, попробуйте позднее";
            return new ResponseUsersDto(null, false, error);
        }
    }

    @Override
    public ResponseUsersDto getUsersByLastName(String lastName) {
        try {
            List<User> users = usersSearchService.getUsersByLastName(lastName);
            log.info("Get users by last name was successful. Users: {}", users);
            return new ResponseUsersDto(users, true, null);
        } catch (EntityNotFoundException e) {
            log.error("Get users by last name is failed: {}", e.getMessage());
            String error = String.format("Не удалось получить пользователей c фамилией %s, попробуйте позднее",
                    lastName);
            return new ResponseUsersDto(null, false, error);
        }
    }

    @Override
    public ResponseUsersDto getUsersByFirstName(String firstName) {
        try {
            List<User> users = usersSearchService.getUsersByFirstName(firstName);
            log.info("Get users by first name was successful. Users: {}", users);
            return new ResponseUsersDto(users, true, null);
        } catch (EntityNotFoundException e) {
            log.error("Get users by last name is failed: {}", e.getMessage());
            String error = String.format("Не удалось получить пользователей c именем %s, попробуйте позднее",
                    firstName);
            return new ResponseUsersDto(null, false, error);
        }
    }

    @Override
    public ResponseUsersDto getUsersByFirstNameAndLastName(String lastName, String firstName) {
        try {
            List<User> users = usersSearchService.getUsersByFirstNameAndLastName(lastName, firstName);
            log.info("Get users by full name was successful. Users: {}", users);
            return new ResponseUsersDto(users, true, null);
        } catch (EntityNotFoundException e) {
            log.error("Get users by full name is failed: {}", e.getMessage());
            String error = String.format("Не удалось получить пользователей c именем и фамлией %s %s," +
                    " попробуйте позднее", firstName, lastName);
            return new ResponseUsersDto(null, false, error);
        }
    }
}

