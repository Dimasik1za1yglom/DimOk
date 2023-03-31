package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.exception.UserAddProcessException;
import ru.sen.accountserver.exception.UserDeleteProcessException;
import ru.sen.accountserver.exception.UserUpdateProcessException;
import ru.sen.accountserver.mappers.UserToEntityMapper;
import ru.sen.accountserver.repository.UserRepository;
import ru.sen.accountserver.services.AuthorizationDataService;
import ru.sen.accountserver.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorizationDataService dataService;
    private final UserToEntityMapper userToEntityMapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = UserAddProcessException.class)
    public void addUser(UserDto userForm, String email) throws UserAddProcessException {
        try {
            User user = userRepository.save(userToEntityMapper.userFormToUser(userForm));
            dataService.updateData(email, user);
            log.info("Adding a new user and update his authorization data was successful");
        } catch (Exception e) {
            log.error("Adding a new user and update his authorization data failed: {}", e.getMessage());
            throw new UserAddProcessException("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = UserDeleteProcessException.class)
    public void deleteUser(Long userToDeleteId, String email) throws UserDeleteProcessException {
        log.info("deleting a user to id {}. ", userToDeleteId);
        try {
            AuthorizationData data = dataService.getData(email);
            User user = data.getUser();
            log.info("authorization data, who wants to delete: {}", data);
            if (userToDeleteId.equals(user.getId())
                    || (getUserById(user.getId()).getRole().getId()).equals(2L)) {
                log.info("Satisfaction of the conditions for deleting the user");
                dataService.deleteDataWasSuccessful(userToDeleteId);
                userRepository.deleteById(userToDeleteId);
                log.info("Delete user of authorization data by userId {} was successful", userToDeleteId);
            } else {
                log.error("verification of the conditions for deleting the user failed");
                throw new UserDeleteProcessException("Insufficient rights to delete a user");
            }
        } catch (Exception e) {
            log.error("Delete a user and his authorization data failed: {}", e.getMessage());
            throw new UserDeleteProcessException(e.getMessage());
        }

    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = UserUpdateProcessException.class)
    public void updateUser(UserDto userForm, String email) throws UserUpdateProcessException {
        log.info("Update a user: {}", userForm);
        try {
            User user = userToEntityMapper.userFormToUser(userForm);
            user.setId(dataService.getData(email).getUser().getId());
            userRepository.save(user);
            log.info("User update was successful");
        } catch (Exception e) {
            log.error("Update user and his authorization data false: {}", e.getMessage());
            throw new UserUpdateProcessException(e.getMessage());
        }
    }

    @Override
    public boolean checkIfUserExists(String emailUser) {
        try {
            AuthorizationData data = dataService.getData(emailUser);
            return data.getUser() != null;
        } catch (Exception e) {
            log.error("Not possible to Verification a user and authorization data: {}", e.getMessage());
            return false;
        }
    }
}
