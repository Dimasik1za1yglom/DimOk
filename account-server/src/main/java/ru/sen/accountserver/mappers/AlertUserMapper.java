package ru.sen.accountserver.mappers;

import org.mapstruct.Mapper;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.kafka.producer.model.AlertNewUser;

@Mapper(componentModel = "spring")
public interface AlertUserMapper {
    AlertNewUser userDtoToAlertNewUser(UserDto userDto);
}
