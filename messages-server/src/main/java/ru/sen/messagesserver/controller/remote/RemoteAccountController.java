package ru.sen.messagesserver.controller.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.messagesserver.controller.remote.api.RemoteAccountApi;
import ru.sen.messagesserver.dto.remote.ResponseDialogDto;
import ru.sen.messagesserver.dto.remote.ResponseDto;
import ru.sen.messagesserver.service.DialogService;
import ru.sen.messagesserver.service.ErrorInterceptorService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/dialog")
public class RemoteAccountController implements RemoteAccountApi {

    private final ErrorInterceptorService interceptorService;
    private final DialogService dialogService;

    @Override
    public ResponseDialogDto existsDialog(Long createUserId, Long userId) {
        Long dialogId = dialogService.checkIfDialogExists(createUserId, userId);
        if (dialogId == null) {
            log.info("There is no common dialog for two users");
            return new ResponseDialogDto(null, false, "Общий диалог отсутсвует");
        } else {
            log.info("There is yes common dialog {} for two users", dialogId);
            return new ResponseDialogDto(dialogId, true, null);
        }
    }

    @Override
    public ResponseDto deleteDialogs(Long userId) {
        if (!interceptorService.checkIfDeletingDialogSuccessful(userId)) {
            String error = "Не удалось удалить диалог у пользователя. Попробуйте позднее";
            log.error("Error on deleting a dialog under user id: {}", userId);
            return new ResponseDto(false, error);
        } else {
            log.info("Deleting the dialog was successful user id: {}", userId);
            return new ResponseDto(true, null);
        }
    }

    @Override
    public ResponseDto changeDialogsName(String newNameDialog, Long userId) {
        if (!interceptorService.checkIfChangeDialogsNameSuccessful(newNameDialog, userId)) {
            String error = "Не удалось имзенить имя диалогов. Попробуйте позднее";
            log.error("Error on change name a dialogs under user id: {}", userId);
            return new ResponseDto(false, error);
        } else {
            log.info("Change name dialogs was successful user id: {}", userId);
            return new ResponseDto(true, null);
        }
    }
}
