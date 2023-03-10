package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dao.AuthorizationDataRepository;
import ru.sen.accountserver.dao.UserRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.forms.UserForm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataSource dataSource;
    private final AuthorizationDataRepository dataRepository;
    private final AuthorizationDataService dataService;

    @Override
    public boolean addUser(UserForm userForm, String email) {
        var user = User.builder()
                .firstName(userForm.getFirstName().strip())
                .lastName(userForm.getLastName().strip())
                .birthday(userForm.getBirthday())
                .bio(userForm.getBio().strip())
                .country(userForm.getCountry().strip())
                .city(userForm.getCity().strip())
                .phone(userForm.getPhone().strip())
                .roleId(1L)
                .build();
        AuthorizationData data = dataService.getData(email);
        try {
            return addUserAndAuthorizationData(user, data);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long userToDeleteId, String email) {
        AuthorizationData data = dataService.getData(email);
        if (userToDeleteId.equals(data.getUserId())
                && getUserById(data.getUserId()).getRoleId().equals(2L)) {
            return deleteUserAndAuthorizationData(userToDeleteId);
        } else {
            return false;
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public boolean updateUser(UserForm userForm, String email) {
        var user = User.builder()
                .id(dataService.getData(email).getUserId())
                .firstName(userForm.getFirstName().strip())
                .lastName(userForm.getLastName().strip())
                .birthday(userForm.getBirthday())
                .bio(userForm.getBio().strip())
                .country(userForm.getCountry().strip())
                .city(userForm.getCity().strip())
                .phone(userForm.getPhone().strip())
                .roleId(1L)
                .build();
        return userRepository.updateUser(user);

    }

    @Override
    public boolean userVerification(String emailUser) {
        AuthorizationData data = dataService.getData(emailUser);
        return data.getUserId() != null;
    }

    private boolean deleteUserAndAuthorizationData(Long userToDeleteId) {
        try {
            Connection connection = dataSource.getConnection();
            try (connection) {
                connection.setAutoCommit(false);
                if (dataRepository.deleteDataByUserId(userToDeleteId) &&
                        userRepository.deleteUserById(userToDeleteId)) {
                    throw new SQLException(" not delete data");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addUserAndAuthorizationData(User user, AuthorizationData data) {
        try {
            Connection connection = dataSource.getConnection();
            try (connection) {
                connection.setAutoCommit(false);
                Long userId = userRepository.addUser(user);
                data.setUserId(userId);
                if (!dataRepository.updateData(data)) {
                    throw new SQLException(" not update data");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
