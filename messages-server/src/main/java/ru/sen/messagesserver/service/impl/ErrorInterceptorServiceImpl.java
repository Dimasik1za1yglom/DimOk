package ru.sen.messagesserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.messagesserver.exception.DialogOperationException;
import ru.sen.messagesserver.service.DialogService;
import ru.sen.messagesserver.service.ErrorInterceptorService;

@Service
@RequiredArgsConstructor
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final DialogService dialogService;
    @Override
    public boolean checkIfDeletingDialogSuccessful(Long userId) {
        try {
            dialogService.deleteDialogs(userId);
            return true;
        } catch (DialogOperationException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletingDialogSuccessful(Long userId, Long dialogId) {
        try {
            dialogService.deleteDialog(userId, dialogId);
            return true;
        } catch (DialogOperationException e) {
            return false;
        }
    }
}
