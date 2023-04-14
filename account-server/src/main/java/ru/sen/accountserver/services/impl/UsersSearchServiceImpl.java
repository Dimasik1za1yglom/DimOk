package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<User> getUsersByLastName(String lastName) {
        log.info("getting all users by last name: {}", lastName);
        return userRepository.findAllByLastName(lastName);
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) {
        log.info("getting all users by first name: {}", firstName);
        return userRepository.findAllByFirstName(firstName);
    }

    @Override
    public List<User> getUsersByFirstNameAndLastName(String lastName, String firstName) {
        log.info("getting all users by full name: {} {}", firstName, lastName);
        return userRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }
}
