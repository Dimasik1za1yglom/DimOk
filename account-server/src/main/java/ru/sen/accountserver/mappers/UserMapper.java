package ru.sen.accountserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleRepository rolesRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(rolesRepository.getReferenceById(1L))")
    public abstract User userDtoToUser(UserDto userDto);
}
