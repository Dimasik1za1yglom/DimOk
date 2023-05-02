package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.dto.remote.ResponseDto;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.exception.UserOperationException;
import ru.sen.accountserver.gateway.DialogGateway;
import ru.sen.accountserver.gateway.PostGateway;
import ru.sen.accountserver.gateway.SearchRequestGateway;
import ru.sen.accountserver.mappers.UserMapper;
import ru.sen.accountserver.repository.UserRepository;
import ru.sen.accountserver.services.AuthorizationDataService;
import ru.sen.accountserver.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PostGateway postGateway;
    private final DialogGateway dialogGateway;
    private final UserRepository userRepository;
    private final UserMapper userToEntityMapper;
    private final AuthorizationDataService dataService;
    private final SearchRequestGateway searchRequestGateway;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = UserOperationException.class)
    public void addUser(UserDto userDto, String email) throws UserOperationException {
        try {
            User user = userRepository.save(userToEntityMapper.userDtoToUser(userDto));
            dataService.updateData(email, user);
            log.info("Adding a new user and update his authorization data was successful");
        } catch (Exception e) {
            log.error("Adding a new user and update his authorization data failed: {}", e.getMessage());
            throw new UserOperationException("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = UserOperationException.class)
    public void deleteUser(Long userToDeleteId, String email) throws UserOperationException {
        log.info("deleting a user to id {}. ", userToDeleteId);
        try {
            AuthorizationData data = dataService.getData(email);
            User user = data.getUser();
            log.info("authorization data, who wants to delete: {}", data);
            if (userToDeleteId.equals(user.getId())
                    || (getUserById(user.getId()).getRole().getId()).equals(2L)) {
                log.info("Satisfaction of the conditions for deleting the user");
                dataService.deleteDataByUserId(userToDeleteId);
                ResponseDto response = searchRequestGateway.deleteSearchRequest(userToDeleteId);
                if (response.isSuccess()) {
                    log.info("Delete search requests by user id {} was successful", userToDeleteId);
                } else {
                    log.error("Delete search requests by user id {} is failed: {}", userToDeleteId, response.getMessage());
                }
                response = postGateway.deletePosts(userToDeleteId);
                if (response.isSuccess()) {
                    log.info("Delete posts by id {} was successful", userToDeleteId);
                } else {
                    log.error("Delete posts  by user id {} is failed: {}", userToDeleteId, response.getMessage());
                }
                response = dialogGateway.deleteDialogsByUser(userToDeleteId);
                if (response.isSuccess()) {
                    log.info("Delete dialogs by id {} was successful", userToDeleteId);
                } else {
                    log.error("Delete dialogs  by user id {} is failed: {}", userToDeleteId, response.getMessage());
                }
                userRepository.deleteById(userToDeleteId);
                log.info("Delete user of authorization data by userId {} was successful", userToDeleteId);
            } else {
                log.error("verification of the conditions for deleting the user failed");
                throw new UserOperationException("Insufficient rights to delete a user");
            }
        } catch (Exception e) {
            log.error("Delete a user and his authorization data failed: {}", e.getMessage());
            throw new UserOperationException(e.getMessage());
        }

    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = UserOperationException.class)
    public void updateUser(UserDto userDto, Long userId) throws UserOperationException {
        log.info("Update a user: {}", userDto);
        try {
            User user = userToEntityMapper.userDtoToUser(userDto);
            user.setId(userId);
            userRepository.save(user);
            log.info("User update was successful");
        } catch (Exception e) {
            log.error("Update user and his authorization data false: {}", e.getMessage());
            throw new UserOperationException(e.getMessage());
        }
    }

    @Override
    public boolean checkIfPhoneExists(String phone) {
        String phoneNotEmpty = phone.strip();
        log.info("Checking existing user fields phone : {}", phoneNotEmpty);
        return userRepository.existsByPhone(phone);
    }
}
