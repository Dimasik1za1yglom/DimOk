package ru.sen.messagesserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.messagesserver.dto.remote.ResponseDto;

public interface RemoteAccountApi {

    @GetMapping("/exists")
    ResponseDto existsDialog(@RequestParam Long createUserId, Long userId);
}
