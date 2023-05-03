package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.accountserver.dto.remote.SearchRequestDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.repository.UserRepository;
import ru.sen.accountserver.services.UsersSearchService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersSearchServiceImpl implements UsersSearchService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("getting all users");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersBySearchFilter(SearchRequestDto searchFilter) {
        log.info("getting all users by search filter name: {}", searchFilter);
        if (searchFilter.getFirstName().isBlank()) {
            log.info("User search filter bar has only the last name: {}", searchFilter.getLastName());
            return userRepository.findAllByLastName(searchFilter.getLastName());
        } else if (searchFilter.getLastName().isBlank()) {
            log.info("User search filter bar has only the first name: {}", searchFilter.getFirstName());
            return userRepository.findAllByFirstName(searchFilter.getFirstName());
        } else {
            log.info("In the search filter bar user has a last name with a first name");
            return userRepository.findAllByFirstNameAndLastName(searchFilter.getFirstName(),
                    searchFilter.getLastName());
        }
    }
}
