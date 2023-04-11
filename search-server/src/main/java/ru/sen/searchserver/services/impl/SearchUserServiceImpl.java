package ru.sen.searchserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sen.searchserver.dto.SearchRequestDto;
import ru.sen.searchserver.entity.User;
import ru.sen.searchserver.services.SearchUserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchUserServiceImpl implements SearchUserService {

    @Override
    public List<User> getAllUsersByTextRequest(SearchRequestDto searchRequestDto) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
