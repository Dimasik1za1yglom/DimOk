package ru.sen.messagesserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.repository.DialogRepository;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    @Autowired
    protected DialogRepository dialogRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "timeCreation", source = "dateTime")
    @Mapping(target = "dialogId", expression = "java(dialogRepository.getReferenceById(messageDto.getDialogId()))")
    public abstract Message messageDtoToMessage(MessageDto messageDto, LocalDateTime dateTime, Long userId);
}
