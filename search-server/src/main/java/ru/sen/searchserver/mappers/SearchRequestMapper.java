package ru.sen.searchserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.SearchRequest;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface SearchRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "timeCreation", source = "dateTime")
    @Mapping(target = "textRequest", expression = "java(requestDtoToTextRequest(searchRequestDto))")
    SearchRequest searchRequestDtoToSearchRequest(SearchRequestDto searchRequestDto, LocalDateTime dateTime, Long userId);

    default String requestDtoToTextRequest(SearchRequestDto searchRequestDto) {
        return String.format("%s %s", searchRequestDto.getFirstName(), searchRequestDto.getLastName());
    }
}
