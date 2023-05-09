package ru.sen.searchserver.controller.remote.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.searchserver.dto.remote.ResponseDto;

public interface RemoteAccountApi {

    @PostMapping("/delete")
    ResponseDto deleteSearchRequests(@RequestParam Long userId);
}
