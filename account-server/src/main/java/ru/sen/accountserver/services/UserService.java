package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.forms.UserForm;

public interface UserService {

    boolean addUser(UserForm userForm, String email);

    boolean deleteUser(Long userToDeleteId, String email);

    User getUserById(Long userId);

    boolean updateUser(UserForm userForm, String emailUser);

    boolean userVerification(String emailUser);
}
