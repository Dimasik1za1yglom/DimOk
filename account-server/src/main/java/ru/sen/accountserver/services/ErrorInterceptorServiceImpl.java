package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.forms.UserForm;

@RequiredArgsConstructor
@Service
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final UserService userService;

    @Override
    public boolean checkingAddingUser(UserForm userForm, String email) {
        try {
            userService.addUser(userForm, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkingDeletingUser(Long userToDeleteId, String email) {
        try {
            userService.deleteUser(userToDeleteId, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkingUpdateUser(UserForm userForm, String email) {
        try {
            userService.updateUser(userForm, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
