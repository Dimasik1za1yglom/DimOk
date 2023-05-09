package ru.sen.accountserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sen.accountserver.dto.remote.ResponseUsersDto;
import ru.sen.accountserver.dto.remote.SearchRequestDto;

public interface RemoteSearchApi {

    @PostMapping("/filter")
    ResponseUsersDto getUsersByFilter(@RequestBody SearchRequestDto searchFilter);

    @GetMapping("/all")
    ResponseUsersDto getAllUsers();
}
