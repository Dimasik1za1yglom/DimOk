package ru.sen.messagesserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.entity.MessageOutput;

@Mapper(componentModel = "spring")
public interface MessageOutputMapper {

    @Mapping(target = "userName", source = "userName")
    MessageOutput messageToMessageOutput(Message message, String userName);
}
