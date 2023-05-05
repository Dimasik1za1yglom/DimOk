package ru.sen.accountserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.accountserver.dto.remote.ResponseUserDto;

public interface RemoteMessageApi {

    @GetMapping("/id")
    ResponseUserDto getUserNameByUserId(@RequestParam Long userId);
}
