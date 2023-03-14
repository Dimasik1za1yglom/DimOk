package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        try {
            AuthorizationData data = dataService.getData(email);
            return addUserAndAuthorizationData(user, data);
        } catch (Exception e) {
            log.error("it was not possible to add a user and update his authorization data: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long userToDeleteId, String email) {
        try {
            AuthorizationData data = dataService.getData(email);
            if (userToDeleteId.equals(data.getUserId())
                    && getUserById(data.getUserId()).getRoleId().equals(2L)) {
                log.info("satisfaction of the conditions for deleting the user");
                return deleteUserAndAuthorizationData(userToDeleteId);
            }
        } catch (Exception e) {
            log.error("it was not possible to delete a user and authorization data: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public boolean updateUser(UserForm userForm, String email) {
        try {
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
        } catch (Exception e) {
            log.error("it was not possible to update a user and authorization data: {}", e.getMessage());
            return false;
        }

    }

    @Override
    public boolean userVerification(String emailUser) {
        try {
            AuthorizationData data = dataService.getData(emailUser);
            return data.getUserId() != null;
        } catch (Exception e) {
            log.error("it was not possible to Verification a user and authorization data: {}", e.getMessage());
            return false;
        }
    }

    private boolean deleteUserAndAuthorizationData(Long userToDeleteId) {
        log.info("the beginning of the transaction by delete a user and authorization data");
        try {
            Connection connection = dataSource.getConnection();
            try (connection) {
                connection.setAutoCommit(false);
                if (dataRepository.deleteDataByUserId(userToDeleteId) &&
                        userRepository.deleteUserById(userToDeleteId)) {
                    throw new SQLException(" not delete data");
                }
                connection.commit();
                log.info("successful transaction for by delete a user and authorization data");
            } catch (SQLException e) {
                connection.rollback();
                log.error("error when making a transaction for by delete a user and to authorization data: {}",
                        e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            log.error("error with connecting to datasource: {}", e.getMessage());
            return false;
        }
        return true;
    }

    private boolean addUserAndAuthorizationData(User user, AuthorizationData data) {
        log.info("the beginning of the transaction by adding a user and binding to his authorization data");
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
                log.info("successful transaction for adding a user and linking it to authorization data");
            } catch (SQLException e) {
                connection.rollback();
                log.error("error when making a transaction for adding a user and linking it to authorization data: {}",
                        e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            log.error("error with connecting to datasource: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
