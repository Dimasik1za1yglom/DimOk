package ru.sen.searchserver.services;

import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;

import java.util.List;

public interface SearchUserService {

    List<User> getAllUsersByTextRequest(SearchRequestDto searchRequestDto);

    List<User> getAllUsers();

}
