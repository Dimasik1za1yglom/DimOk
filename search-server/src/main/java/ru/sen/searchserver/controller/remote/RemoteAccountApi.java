package ru.sen.searchserver.controller.remote;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.searchserver.dto.remote.ResponseSearchRequestDto;

public interface RemoteAccountApi {

    @PostMapping("/delete")
    ResponseSearchRequestDto deleteSearchRequests(@RequestParam Long userId);
}
