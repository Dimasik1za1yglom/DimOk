package ru.sen.accountserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.repository.RoleRepository;
import ru.sen.accountserver.services.RoleService;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleService roleService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(roleService.getRoleById(roleId))")
    public abstract User userDtoToUser(UserDto userDto, Long roleId);
}
