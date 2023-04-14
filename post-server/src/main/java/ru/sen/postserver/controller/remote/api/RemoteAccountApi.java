package ru.sen.postserver.controller.remote.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.postserver.dto.remote.ResponseDto;

public interface RemoteAccountApi {

    @PostMapping("/delete")
    ResponseDto deletePosts(@RequestParam Long userId);
}
