package ru.sen.messagesserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.messagesserver.dto.remote.ResponseDialogDto;
import ru.sen.messagesserver.dto.remote.ResponseDto;

public interface RemoteAccountApi {

    @GetMapping("/exists")
    ResponseDialogDto existsDialog(@RequestParam Long createUserId, Long userId);

    @PostMapping("/delete")
    ResponseDto deleteDialogs(@RequestParam Long userId);
}
