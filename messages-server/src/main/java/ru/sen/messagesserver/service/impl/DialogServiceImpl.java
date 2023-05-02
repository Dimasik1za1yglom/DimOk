package ru.sen.messagesserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;
import ru.sen.messagesserver.exception.DialogOperationException;
import ru.sen.messagesserver.mapper.DialogMapper;
import ru.sen.messagesserver.repository.DialogRepository;
import ru.sen.messagesserver.service.DialogService;
import ru.sen.messagesserver.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;
    private final DialogMapper dialogMapper;
    private final MessageService messageService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = DialogOperationException.class)
    public Long createDialog(List<DialogDto> dialogsDto, List<Long> usersId)
            throws DialogOperationException {
        Long number = usersId.get(0) * 100L + usersId.get(1);
        log.info("create dialogs: {} by usersId: {}", dialogsDto, usersId);
        try {
            List<Dialog> dialogs = IntStream.range(0, usersId.size())
                    .mapToObj(i -> dialogMapper.dialogDtoToDialog(dialogsDto.get(i), usersId.get(i), number))
                    .collect(Collectors.toList());
            log.info("creating a list dialogs for users: {}", dialogs);
            dialogRepository.saveAll(dialogs);
            log.info("Save a new dialogs was successful");
            return number;
        } catch (Exception E) {
            log.error("Save a new dialogs is failed");
            throw new DialogOperationException("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = DialogOperationException.class)
    public void deleteDialogs(Long userId) throws DialogOperationException {
        log.info("deleting a dialog user to id {}. ", userId);
        try {
            List<Dialog> dialogs = dialogRepository.deleteDialogsByUserId(userId);
            dialogs.stream()
                    .filter(dialog -> dialogRepository.countDialogByDialogId(dialog.getDialogId()) == 0)
                    .forEach(dialog -> messageService.deleteAllMessageByDialogId(dialog.getDialogId()));
            log.info("Deleting all messages of a certain dialog if this dialog is no longer there");
            log.info("Delete dialogs by user id {} was successful", userId);
        } catch (Exception e) {
            log.error("Delete dialogs by user id {} failed: {}", userId, e.getMessage());
            throw new DialogOperationException(e.getMessage());
        }

    }

    @Override
    public List<Dialog> getAllDialogsByUserId(Long userId) {
        log.info("get list<Dialog> by userId: {}", userId);
        return dialogRepository.findAllByUserId(userId);
    }

    @Override
    public Long checkIfDialogExists(Long createUserId, Long userId) {
        log.info("checking for the existence of a common dialog for two users");
        return dialogRepository.getDialogIdByUsersId(createUserId, userId);
    }
}
