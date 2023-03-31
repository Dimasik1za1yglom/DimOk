package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.services.ErrorInterceptorService;
import ru.sen.accountserver.services.UserService;

@Service
@RequiredArgsConstructor
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final UserService userService;

    @Override
    public boolean checkIfAddingUserSuccessful(UserDto userForm, String email) {
        try {
            userService.addUser(userForm, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletingUserSuccessful(Long userToDeleteId, String email) {
        try {
            userService.deleteUser(userToDeleteId, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkingUpdateUser(UserDto userForm, String email) {
        try {
            userService.updateUser(userForm, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
