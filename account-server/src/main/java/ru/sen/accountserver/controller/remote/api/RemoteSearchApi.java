package ru.sen.accountserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.accountserver.dto.remote.ResponseUsersDto;
import ru.sen.accountserver.dto.remote.SearchRequestDto;

public interface RemoteSearchApi {

    @GetMapping("/filter")
    ResponseUsersDto getUsersByFilter(@RequestParam SearchRequestDto searchFilter);

    @GetMapping("/all")
    ResponseUsersDto getAllUsers();
}
