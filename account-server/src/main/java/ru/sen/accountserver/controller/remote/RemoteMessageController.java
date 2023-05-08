package ru.sen.accountserver.controller.remote;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.accountserver.controller.remote.api.RemoteMessageApi;
import ru.sen.accountserver.dto.remote.ResponseUserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.services.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/user")
public class RemoteMessageController implements RemoteMessageApi {

    private final UserService userService;

    @Override
    public ResponseUserDto getUserNameByUserId(Long userId) {
        try {
            User user = userService.getUserById(userId);
            log.info("/app/user/id: getting a user by id was successful: {}", user);
            return new ResponseUserDto(String.format("%s %s", user.getFirstName(),
                    user.getLastName()), true, null);
        } catch (EntityNotFoundException e) {
            log.error("/app/user/id: Getting a user is failed: {}", e.getMessage());
            return new ResponseUserDto(null, false, "Пользователь отсутсвует");
        }
    }
}