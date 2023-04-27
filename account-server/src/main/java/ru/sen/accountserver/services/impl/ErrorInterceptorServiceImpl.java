package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.exception.UserOperationException;
import ru.sen.accountserver.services.ErrorInterceptorService;
import ru.sen.accountserver.services.UserService;

@Service
@RequiredArgsConstructor
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final UserService userService;

    @Override
    public boolean checkIfAddingUserSuccessful(UserDto userDto, String email) {
        try {
            userService.addUser(userDto, email);
            return true;
        } catch (UserOperationException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletingUserSuccessful(Long userToDeleteId, String email) {
        try {
            userService.deleteUser(userToDeleteId, email);
            return false;
        } catch (UserOperationException e) {
            return true;
        }
    }

    @Override
    public boolean checkIfUpdateUserSuccessful(UserDto userDto, Long userId) {
        try {
            userService.updateUser(userDto, userId);
            return true;
        } catch (UserOperationException e) {
            return false;
        }
    }
}
