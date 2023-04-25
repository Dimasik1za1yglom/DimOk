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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;
    private final DialogMapper dialogMapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = DialogOperationException.class)
    public void createDialog(DialogDto dialogDto, List<Long> usersId)
            throws DialogOperationException {
        Long number = usersId.get(0) + 100;
        log.info("create dialog: {} by usersId: {}", dialogDto, usersId);
        try {
            List<Dialog> dialogs = usersId.stream()
                    .map(x -> dialogMapper.dialogDtoToDialog(dialogDto, x, number))
                    .collect(Collectors.toList());
            log.info("creating a list dialogs for users: {}", dialogs);
            dialogRepository.saveAll(dialogs);
            log.info("Save a new dialogs was successful");
        } catch (Exception E) {
            log.error("Save a new dialogs is failed");
            throw new DialogOperationException("Throwing exception for demoing rollback");
        }

    }

    @Override
    public List<Dialog> getAllDialogsByUserId(Long userId) {
        log.info("get list<Dialog> by userId: {}", userId);
        return dialogRepository.findAllByUserId(userId);
    }
}
