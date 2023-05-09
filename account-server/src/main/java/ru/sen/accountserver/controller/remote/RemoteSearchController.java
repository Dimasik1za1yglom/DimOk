package ru.sen.accountserver.controller.remote;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.accountserver.controller.remote.api.RemoteSearchApi;
import ru.sen.accountserver.dto.remote.ResponseUsersDto;
import ru.sen.accountserver.dto.remote.SearchRequestDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.services.UsersSearchService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class RemoteSearchController implements RemoteSearchApi {

    private final UsersSearchService usersSearchService;

    @Override
    public ResponseUsersDto getUsersByFilter(SearchRequestDto searchFilter) {
        try {
            List<User> users = usersSearchService.getUsersBySearchFilter(searchFilter);
            log.info("Get users by search filter was successful. Users: {}", users);
            return new ResponseUsersDto(users, true, null);
        } catch (EntityNotFoundException e) {
            log.error("Get all users by search filter  is failed: {}", e.getMessage());
            String error = "Не удалось получить пользователей по фильтру, попробуйте позднее";
            return new ResponseUsersDto(null, false, error);
        }
    }

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
}

