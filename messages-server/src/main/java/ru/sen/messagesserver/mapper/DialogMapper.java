package ru.sen.messagesserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;

@Mapper(componentModel = "spring")
public interface DialogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "dialogId", source = "dialogId")
    @Mapping(target = "name", expression = "java(dialogDtoToNameDialog(dialogDto))")
    Dialog dialogDtoToDialog(DialogDto dialogDto, Long userId, Long dialogId);

    default String dialogDtoToNameDialog(DialogDto dialogDto) {
        return String.format("%s %s", dialogDto.getFirstName(), dialogDto.getLastName());
    }
}
