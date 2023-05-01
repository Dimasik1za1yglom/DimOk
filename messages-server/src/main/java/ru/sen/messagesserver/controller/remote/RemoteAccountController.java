package ru.sen.messagesserver.controller.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.messagesserver.controller.remote.api.RemoteAccountApi;
import ru.sen.messagesserver.dto.remote.ResponseDto;
import ru.sen.messagesserver.service.DialogService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/dialog")
public class RemoteAccountController implements RemoteAccountApi {

    private final DialogService dialogService;

    @Override
    public ResponseDto existsDialog(Long createUserId, Long userId) {
        if (dialogService.checkIfDialogExists(createUserId, userId)) {
            log.info("There is yes common dialog for two users");
            return new ResponseDto(true, null);
        } else {
            log.info("There is no common dialog for two users");
            return new ResponseDto(false, "Общий диалог отсутсвует");
        }
    }
}
