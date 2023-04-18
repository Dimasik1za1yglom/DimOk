package ru.sen.postserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "timeCreation", source = "dateTime")
    Post postDtoToPost(PostDto postDto, LocalDateTime dateTime, Long userId);
}
